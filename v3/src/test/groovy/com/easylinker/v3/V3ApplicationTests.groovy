package com.easylinker.v3

import cn.hutool.crypto.digest.DigestUtil
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.model.DeviceData
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import com.easylinker.v3.modules.message.model.Message
import com.easylinker.v3.modules.message.model.MessageState
import com.easylinker.v3.modules.message.service.MessageService
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import com.easylinker.v3.modules.schedule.model.JobEntity
import com.easylinker.v3.modules.schedule.service.SchedulePostJobService
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.model.Role
import com.easylinker.v3.modules.user.service.RoleService
import com.easylinker.v3.modules.user.service.UserService
import org.apache.commons.codec.digest.DigestUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class V3ApplicationTests {
    @Autowired
    UserService userService
    @Autowired
    RoleService roleService
    @Autowired
    DeviceService deviceService

    /**
     * 如需添加测试用户可以在这里调用
     */
    @Test
    void contextLoads() {

        //addUser()
        //addAdmin()
    }

    /**
     * 测试添加一个任务
     */
    @Autowired
    SchedulePostJobService schedulePostJobService

    @Test
    void addJob() {
        JobEntity jobEntity = new JobEntity(
                userSecurityId: 1,
                bizSecurityId: "1000000",
                jobName: "HelloWorld",
                jobGroup: "Group",
                bizJobData: [K1: "V1", K2: "V2"],
                cron: "0/3 * * * * ? ",
                status: "NORMAL",
                jobDescription: "测试Job"
        )

        schedulePostJobService.save(jobEntity)
    }
    /**
     * 添加一个管理员
     */
    void addAdmin() {
        AppUser appUser = new AppUser(principle: "admin",
                password: DigestUtils.sha256Hex("public"),
                email: "easylinker@admin.com",
                name: "测试管理员"
        )
        userService.save(appUser)
        roleService.save(new Role(name: "BASE_ROLE", info: "基本权限", appUser: appUser))
        roleService.save(new Role(name: "ADMIN", info: "管理员", appUser: appUser))

    }
    /**
     * 添加一个普通用户
     */

    @Autowired
    SceneService sceneService

    void addUser() {
        AppUser appUser = new AppUser(principle: "easylinker",
                password: DigestUtils.sha256Hex("public"),
                email: "easylinker@user.com",
                name: "测试用户"
        )
        userService.save(appUser)
        roleService.save(new Role(name: "BASE_ROLE", info: "基本权限", appUser: appUser))

        Scene scene = new Scene(name: "测试场景", info: "自动生成的测试场景", appUser: appUser)
        sceneService.save(scene)
        MQTTDevice mqttDevice = new MQTTDevice(name: "测试设备",
                info: "临时测试设备",
                clientId: UUID.randomUUID().toString().replace("-", ""),
                password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                username: UUID.randomUUID().toString().replace("-", ""),
                deviceType: DeviceType.VALUE,
                appUser: appUser,
                scene: scene,
                deviceProtocol: DeviceProtocol.MQTT)
        deviceService.save(mqttDevice)
    }


    @Autowired
    MessageService messageService

    @Test
    void addMessage() {
        for (int i = 0; i < 36; i++) {
            Message message = new Message(
                    userSecurityId: "235735abc88d442b8a8b4aed126d2b4f",
                    msgType: 1,
                    producer: "Local",
                    msgContent: "1号设备掉线，请立即检查",
                    messageState: MessageState.NO_READ)
            messageService.save(message)
        }
        //1f66793e82bd423598e8dbbadca4320b
        for (int i = 0; i < 36; i++) {
            Message message = new Message(
                    userSecurityId: "1f66793e82bd423598e8dbbadca4320b",
                    msgType: 1,
                    producer: "Local",
                    msgContent: "1号设备掉线，请立即检查",
                    messageState: MessageState.NO_READ)
            messageService.save(message)
        }
    }
    /**
     * 设备数据测试
     */
    @Autowired
    DeviceDataService deviceDataService

    /**
     * [
     *{*       "dateTime":"Jan",
     *         "field":"temp",
     *         "value":7
     *},
     *{*       "dateTime":"2019",
     *         "field":"hum",
     *         "value":3.9
     *}* ]
     */
    @Test
    void addData() {
        for (int i = 0; i < 2; i++) {
            DeviceData data = new DeviceData()
            data.deviceSecurityId = "e3aad2c17b444a1fb835dc966976b99b"
            JSONObject dataJson = new JSONObject()
            dataJson.put("dateTime", 12)
            dataJson.put("field", "Hum")
            dataJson.put("value", 23)
            data.setDeviceType(com.easylinker.framework.common.model.DeviceType.BOOLEAN)
            data.setData(dataJson)
            //deviceDataService.save(data)
            println(JSONObject.toJSONString(data))
        }

    }


}
