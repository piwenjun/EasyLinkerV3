package com.easylinker.v3.modules.test

import com.easylinker.framework.common.model.DeviceData
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.config.security.RequireAuthRoles
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateEcho
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 * Created by admin on 2019/6/10 8:55
 *
 */

@RestController
class TestController extends AbstractController {

    TestController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @RequestMapping("/test")
    R test() {
        R.okWithData("测试成功:" + new Date())
    }

    @RequireAuthRoles(roles = ['ADMIN', 'BASE_ROLE'])
    @RequestMapping("/testRole1")
    R testRole1() {
        println(getCurrentUser().id)

        R.okWithData(['ADMIN', 'BASE_ROLE'])
    }

    @RequestMapping("/testRole2")
    R testRole2() {

        R.okWithData("公共接口")
    }


    /**
     * 测试设备上传数据
     * @return
     */
    @Autowired
    DeviceService deviceService

    @Autowired
    DeviceDataService deviceDataService

    @PostMapping("/testPush")
    R testPush(@RequestBody PushForm pushForm) {

        AbstractDevice abstractDevice = deviceService.detail(pushForm.deviceSecurityId, pushForm.deviceProtocol)

        if (abstractDevice) {
            switch (pushForm.deviceProtocol) {
                case DeviceProtocol.MQTT:
                    MQTTDevice device = abstractDevice as MQTTDevice
                    if (device.deviceType == DeviceType.BOOLEAN) {
                        DeviceData data = new DeviceData()
                        data.setInfo("测试开关数据")
                        data.setDataType(DeviceType.BOOLEAN.toString())
                        data.setDeviceSecurityId(device.securityId)
                        data.setData(pushForm.data.toString())
                        deviceDataService.save(data)
                        return R.ok("数据提交成功")
                    } else {
                        return R.ok("测试数据")

                    }

                default: return R.ok("测试数据")

            }
        }

        return R.ok("测试数据")

    }

    /**
     * 测试添加操作日志数据
     * @param pushForm
     * @return
     */

    @Autowired
    MongoTemplate mongoTemplate

    @GetMapping("/testOperate")
    R testOperate(@RequestParam String sid) {
        for (int i = 0; i < 36; i++) {
            DeviceOperateLog deviceOperateLog = new DeviceOperateLog()
            deviceOperateLog.setSecurityId("c6d56ebc13b9490795e15d7a4ee5f5cc")
            deviceOperateLog.setData("01010101")
            deviceOperateLog.setEvent("关机")
            // deviceOperateLog.setCreateTime(new Date())
            deviceOperateLog.setOperate("按钮点击")
            deviceOperateLog.setDeviceSecurityId(sid)
            mongoTemplate.save(deviceOperateLog, "DEVICE_OPERATE_LOG")
            DeviceOperateEcho deviceOperateEcho = new DeviceOperateEcho()
            deviceOperateEcho.setDeviceOperateLogSecurityId(deviceOperateLog.securityId)
            deviceOperateEcho.setData("操作成功")
            //deviceOperateEcho.setCreateTime(new Date())

            mongoTemplate.save(deviceOperateEcho, "DEVICE_OPERATE_ECHO")

        }


        return R.ok("测试数据")

    }

}


class PushForm {
    String deviceSecurityId
    DeviceProtocol deviceProtocol
    List<Boolean> data

}