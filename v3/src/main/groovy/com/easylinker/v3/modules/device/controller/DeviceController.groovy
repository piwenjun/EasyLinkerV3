package com.easylinker.v3.modules.device.controller

import cn.hutool.crypto.digest.DigestUtil
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.device.form.COAPDeviceForm
import com.easylinker.v3.modules.device.form.HTTPDeviceForm
import com.easylinker.v3.modules.device.form.MQTTDeviceForm
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.model.TopicAcl
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * @author wwhai* @date 2019/6/29 23:00
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/device")
class DeviceController extends AbstractController {
    @Autowired
    DeviceService deviceService

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }


    @PostMapping("/addHttp")
    R addHTTP(@RequestBody @Valid HTTPDeviceForm httpDeviceForm) {
        HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.name,
                info: httpDeviceForm.info,
                deviceType: httpDeviceForm.deviceType,
                appUser: getCurrentUser(),
                deviceProtocol: DeviceProtocol.HTTP)
        deviceService.add(httpDevice, DeviceProtocol.HTTP)
        return R.ok()

    }

    @PostMapping("/addCoap")
    R addCOAP(@RequestBody @Valid COAPDeviceForm coapDeviceForm) {
        COAPDevice coapDevice = new COAPDevice(name: coapDeviceForm.name,
                info: coapDeviceForm.info,
                token: UUID.randomUUID().toString().replace("-", ""),
                deviceType: coapDeviceForm.deviceType,
                appUser: getCurrentUser(),
                deviceProtocol: DeviceProtocol.COAP)
        deviceService.add(coapDevice, DeviceProtocol.COAP)
        return R.ok()

    }
    /**
     * 【/client/{user_id}/{client_id}/{方向}/#】
     * @param mqttDeviceForm
     * @return
     */

    @Autowired
    TopicAclService topicAclService

    @PostMapping("/addMqtt")
    @Transactional(rollbackFor = Exception.class)
    R addMQTT(@RequestBody @Valid MQTTDeviceForm mqttDeviceForm) {
        MQTTDevice mqttDevice = new MQTTDevice(name: mqttDeviceForm.name,
                info: mqttDeviceForm.info,
                clientId: UUID.randomUUID().toString().replace("-", ""),
                password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                username: UUID.randomUUID().toString().replace("-", ""),
                deviceType: mqttDeviceForm.deviceType,
                appUser: getCurrentUser(),
                deviceProtocol: DeviceProtocol.COAP)

        //默认给两个权限 对自己的频道进行PUB和SUB
        List<TopicAcl> topicAcls = new ArrayList<>()
        TopicAcl inAcl = new TopicAcl(ip: "0.0.0.0", access: 1, topic: "/device/" + mqttDevice.getSecurityId() + "/in", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        TopicAcl outAcl = new TopicAcl(ip: "0.0.0.0", access: 2, topic: "/device/" + mqttDevice.getSecurityId() + "/out", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        topicAclService.save(inAcl)
        topicAclService.save(outAcl)
        topicAcls.add(inAcl)
        topicAcls.add(outAcl)
        mqttDevice.setTopicAcls(topicAcls)
        deviceService.add(mqttDevice, DeviceProtocol.MQTT)
        return R.ok()

    }

    static void main(String[] args) {
        println(JSONObject.toJSONString(new MQTTDevice(name: "树莓派",
                info: "测试用的数据",
                deviceType: DeviceType.VALUE,
                username: "",
                password: "",
                clientId: "",
                deviceProtocol: DeviceProtocol.MQTT)))
    }

}
