package com.easylinker.v3.modules.device.controller

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.v3.modules.device.form.*
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.model.TopicAcl
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

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
    @Autowired
    SceneService sceneService

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }


    @PostMapping("/addHttp")
    R addHTTP(@RequestBody @Valid HTTPDeviceForm httpDeviceForm) {
        Scene scene = sceneService.findBySecurityId(httpDeviceForm.sceneSecurityId)
        if (scene) {

            HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.name,
                    info: httpDeviceForm.info,
                    deviceType: httpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    scene: scene,
                    deviceProtocol: DeviceProtocol.HTTP)
            deviceService.add(httpDevice, DeviceProtocol.HTTP)
            return R.ok(0, "添加成功")

        } else {
            return R.error(5, "场景不存在")
        }

    }

    @PostMapping("/addCoap")
    R addCOAP(@RequestBody @Valid COAPDeviceForm coapDeviceForm) {
        Scene scene = sceneService.findBySecurityId(coapDeviceForm.sceneSecurityId)
        if (scene) {

            COAPDevice coapDevice = new COAPDevice(name: coapDeviceForm.name,
                    info: coapDeviceForm.info,
                    token: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: coapDeviceForm.deviceType,
                    scene: scene,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.COAP)
            deviceService.add(coapDevice, DeviceProtocol.COAP)
            return R.ok(0, "添加成功")
        } else {
            return R.error(5, "场景不存在")

        }


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
        Scene scene = sceneService.findBySecurityId(mqttDeviceForm.sceneSecurityId)
        if (scene) {
            MQTTDevice mqttDevice = new MQTTDevice(name: mqttDeviceForm.name,
                    info: mqttDeviceForm.info,
                    clientId: UUID.randomUUID().toString().replace("-", ""),
                    password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    username: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: mqttDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    scene: scene,
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
            return R.ok(0, "添加成功")

        } else {
            return R.error(5, "场景不存在")

        }


    }
    /**
     * MQTT设备搜索
     *      * String username
     *      * String clientId
     *      * boolean online
     *      * String name
     *      * String info
     *      * DeviceProtocol deviceProtocol
     *      * DeviceType deviceType
     * @param SearchMqttForm
     * @return
     */


    @Autowired
    UserService userService

    @Transactional
    @PostMapping("/searchMqtt/{page}/{size}")
    R searchMqtt(@PathVariable int page, @PathVariable int size, @RequestBody @Valid SearchMqttForm searchMqttForm) {

        AppUser appUser = userService.findBySecurityId(getCurrentUser().securityId)

        Page<MQTTDevice> mqttDevicePage = deviceService.searchMqtt(new MQTTDevice(
                username: searchMqttForm.username,
                clientId: searchMqttForm.clientId,
                online: searchMqttForm.online,
                name: searchMqttForm.name,
                deviceProtocol: searchMqttForm.deviceProtocol,
                deviceType: searchMqttForm.deviceType,
                appUser: appUser,
                info: searchMqttForm.info),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

        return R.okWithData(mqttDevicePage)
    }

    /**
     * 搜索HTTP设备
     * @param searchHttpForm
     * @return
     */

    @PostMapping("/searchHttp")
    R searchHttp(@RequestBody @Valid SearchHttpForm searchHttpForm) {


        return R.ok()
    }
    /**
     * 搜索COAP设备
     * @param searchCoapForm
     * @return
     */

    @PostMapping("/searchCoap")
    R searchCoap(@RequestBody @Valid SearchCoapForm searchCoapForm) {


        return R.ok()
    }

}
