package com.easylinker.v3

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
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

    void addUser() {
        AppUser appUser = new AppUser(principle: "easylinker",
                password: DigestUtils.sha256Hex("public"),
                email: "easylinker@user.com",
                name: "测试用户"
        )
        userService.save(appUser)
        roleService.save(new Role(name: "BASE_ROLE", info: "基本权限", appUser: appUser))

        MQTTDevice mqttDevice = new MQTTDevice(name: "测试设备",
                info: "临时测试设备",
                clientId: UUID.randomUUID().toString().replace("-", ""),
                password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                username: UUID.randomUUID().toString().replace("-", ""),
                deviceType: DeviceType.VALUE,
                appUser: appUser,
                scene: null,
                deviceProtocol: DeviceProtocol.MQTT)
        deviceService.save(mqttDevice)
    }
}
