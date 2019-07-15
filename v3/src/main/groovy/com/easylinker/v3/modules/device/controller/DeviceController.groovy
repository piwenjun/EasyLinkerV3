package com.easylinker.v3.modules.device.controller

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.model.FieldDesc
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.v3.modules.device.form.CoAPDeviceForm
import com.easylinker.v3.modules.device.form.HTTPDeviceForm
import com.easylinker.v3.modules.device.form.MQTTDeviceForm
import com.easylinker.v3.modules.device.form.TerminalHostDeviceForm
import com.easylinker.v3.modules.device.model.CoAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.model.TerminalHostDevice
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
import java.lang.reflect.Field

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


    private R addDevice(HTTPDeviceForm httpDeviceForm) {

    }
    /**
     * 添加HTTP设备
     * @param httpDeviceForm
     * @return
     */

    @PostMapping("/addHTTP")
    R addHTTP(@RequestBody @Valid HTTPDeviceForm httpDeviceForm) {
        if (httpDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(httpDeviceForm.sceneSecurityId)
            if (scene) {

                HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.name,
                        info: httpDeviceForm.info,
                        deviceType: httpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        deviceProtocol: DeviceProtocol.HTTP)
                deviceService.add(httpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")
            }

        } else {

            HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.name,
                    info: httpDeviceForm.info,
                    deviceType: httpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    deviceProtocol: DeviceProtocol.HTTP)
            deviceService.add(httpDevice)
            return R.ok("添加成功")


        }

    }
    /**
     * 添加CoAP设备
     * @param CoAPDeviceForm
     * @return
     */

    @PostMapping("/addCoAP")
    R addCoAP(@RequestBody @Valid CoAPDeviceForm CoAPDeviceForm) {
        if (CoAPDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(CoAPDeviceForm.sceneSecurityId)
            if (scene) {

                CoAPDevice CoAPDevice = new CoAPDevice(name: CoAPDeviceForm.name,
                        info: CoAPDeviceForm.info,
                        token: UUID.randomUUID().toString().replace("-", ""),
                        deviceType: CoAPDeviceForm.deviceType,
                        scene: scene,
                        appUser: getCurrentUser(),
                        deviceProtocol: DeviceProtocol.CoAP)
                deviceService.add(CoAPDevice)
                return R.ok("添加成功")
            } else {
                return R.error("场景不存在")

            }

        } else {

            CoAPDevice CoAPDevice = new CoAPDevice(name: CoAPDeviceForm.name,
                    info: CoAPDeviceForm.info,
                    token: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: CoAPDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.CoAP)
            deviceService.add(CoAPDevice)
            return R.ok("添加成功")


        }


    }
    /**
     * 添加MQTT设备
     * @param mqttDeviceForm
     * @return
     */

    @Autowired
    TopicAclService topicAclService

    @PostMapping("/addMQTT")
    @Transactional(rollbackFor = Exception.class)
    R addMQTT(@RequestBody @Valid MQTTDeviceForm mqttDeviceForm) {

        if (mqttDeviceForm.sceneSecurityId) {
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
                        deviceProtocol: DeviceProtocol.MQTT)

                deviceService.addMqttDevice(mqttDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            MQTTDevice mqttDevice = new MQTTDevice(name: mqttDeviceForm.name,
                    info: mqttDeviceForm.info,
                    clientId: UUID.randomUUID().toString().replace("-", ""),
                    password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    username: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: mqttDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.MQTT)
            deviceService.addMqttDevice(mqttDevice)
            return R.ok("添加成功")
        }


    }


    /**
     * 添加终端设备
     * @param terminalHostDeviceForm
     * @return
     */
    @PostMapping("/addTerminal")
    @Transactional(rollbackFor = Exception.class)
    R addTerminal(@RequestBody @Valid TerminalHostDeviceForm terminalHostDeviceForm) {
        if (terminalHostDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(terminalHostDeviceForm.sceneSecurityId)
            if (scene) {
                TerminalHostDevice terminalHostDevice = new TerminalHostDevice(name: terminalHostDeviceForm.name,
                        info: terminalHostDeviceForm.info,
                        sshUsername: terminalHostDeviceForm.sshUsername,
                        sshPassword: terminalHostDeviceForm.sshPassword,
                        sshPort: terminalHostDeviceForm.sshPort,
                        deviceType: terminalHostDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.TCP)

                deviceService.addTerminal(terminalHostDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }
        } else {
            TerminalHostDevice terminalHostDevice = new TerminalHostDevice(name: terminalHostDeviceForm.name,
                    info: terminalHostDeviceForm.info,
                    sshUsername: terminalHostDeviceForm.sshUsername,
                    sshPassword: terminalHostDeviceForm.sshPassword,
                    sshPort: terminalHostDeviceForm.sshPort,
                    deviceType: terminalHostDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.TCP)
            deviceService.addTerminal(terminalHostDevice)
            return R.ok("添加成功")
        }


    }


    /**
     * 获取支持的设备类型
     * @return
     */
    @GetMapping("/listDeviceType")

    R listDeviceType() {

        List<Map<String, Object>> list = new ArrayList<>()
        for (DeviceType deviceType : DeviceType.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", deviceType.name)
            map.put("key", deviceType)
            list.add(map)
        }
        return R.okWithData(list)
    }

    /**
     * 获取支持的协议类型
     * @return
     */
    @GetMapping("/listProtocolType")
    R listProtocolType() {
        List<Map<String, Object>> list = new ArrayList<>()
        for (DeviceProtocol deviceProtocol : DeviceProtocol.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", deviceProtocol.name)
            map.put("key", deviceProtocol)
            list.add(map)
        }
        return R.okWithData(list)
    }


    /**
     * 获取设备详情
     * @param detailForm
     * @return
     */
    @GetMapping("/detail")
    R detail(@RequestParam @Valid String securityId, @RequestParam DeviceProtocol deviceProtocol) {
        return R.okWithData(deviceService.detail(securityId, deviceProtocol))

    }
    /**
     * MQTT设备搜索
     * @param SearchMqttForm
     * @return
     */

    @Autowired
    UserService userService

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @GetMapping("/searchMQTT")
    R searchMqtt(@RequestParam(required = true) String username,
                 @RequestParam(required = true) String clientId,
                 @RequestParam(required = true) boolean online,
                 @RequestParam(required = true) String name,
                 @RequestParam(required = true) String deviceProtocol,
                 @RequestParam(required = true) String deviceType,
                 @RequestParam(required = true) String info,
                 @RequestParam(required = true) int page,
                 @RequestParam(required = true) int size) {

        Page<MQTTDevice> mqttDevicePage = deviceService.searchMqtt(new MQTTDevice(
                username: username,
                clientId: clientId,
                online: online,
                name: name,
                deviceProtocol: deviceProtocol,
                deviceType: deviceType,
                appUser: getCurrentUser(),
                info: info),

                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

        return R.okWithData(mqttDevicePage)
    }

    /**
     * 搜索HTTP设备
     * @param searchHttpForm
     * @return
     */

    @GetMapping("/searchHTTP")
    R searchHttp(@RequestParam(required = true) int page,
                 @RequestParam(required = true) int size,
                 @RequestParam(required = true) String name,
                 @RequestParam(required = true) String info) {
        Page<HTTPDevice> httpDevicePage = deviceService.searchHttp(new HTTPDevice(
                name: name,
                appUser: getCurrentUser(),
                info: info),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

        return R.okWithData(httpDevicePage)
    }
    /**
     * 搜索CoAP设备
     * @param searchCoAPForm
     * @return
     */

    @PostMapping("/searchCoAP")
    R searchCoAP(@RequestParam(required = true) int page,
                 @RequestParam(required = true) int size,
                 @RequestParam(required = true) String name,
                 @RequestParam(required = true) String info) {
        Page<CoAPDevice> httpDevicePage = deviceService.searchCoAP(new CoAPDevice(
                name: name,
                appUser: getCurrentUser(),
                info: info),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

        return R.okWithData(httpDevicePage)
    }

    /**
     * 查询MQTT设备
     * http://localhost:2500/easyboot/device/list?page=0&size=10
     * @return
     */
    @GetMapping("/listMQTT")
    R listMqtt(@RequestParam int page, @RequestParam int size) {
        return R.okWithData(deviceService.listByUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")), getCurrentUser(), DeviceProtocol.MQTT))

    }

    @GetMapping("/listHTTP")
    R listHttp(@RequestParam int page, @RequestParam int size) {
        return R.okWithData(deviceService.listByUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")), getCurrentUser(), DeviceProtocol.HTTP))

    }

    @GetMapping("/listCoAP")
    R listCoAP(@RequestParam int page, @RequestParam int size) {
        return R.okWithData(deviceService.listByUser(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")), getCurrentUser(), DeviceProtocol.CoAP))

    }


    @GetMapping("/listField")
    R listField(@RequestParam @Valid DeviceProtocol deviceProtocol) {
        List<Map<String, Object>> fieldList = new ArrayList<>()
        switch (deviceProtocol) {

            case DeviceProtocol.MQTT:

                for (Field field : new MQTTDevice().getClass().declaredFields) {
                    FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class)
                    if (fieldDesc) {
                        Map<String, Object> data = new HashMap<>()
                        data.put("title", fieldDesc.title())
                        data.put("dataIndex", field.name)
                        fieldList.add(data)
                    }
                }
                return R.okWithData(fieldList)
            case DeviceProtocol.HTTP:
                for (Field field : new HTTPDevice().getClass().declaredFields) {
                    FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class)
                    if (fieldDesc) {
                        Map<String, Object> data = new HashMap<>()
                        data.put("title", fieldDesc.title())
                        data.put("dataIndex", field.name)
                        fieldList.add(data)
                    }
                }
                return R.okWithData(fieldList)
            case DeviceProtocol.CoAP:
                for (Field field : new CoAPDevice().getClass().declaredFields) {
                    FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class)
                    if (fieldDesc) {
                        Map<String, Object> data = new HashMap<>()
                        data.put("title", fieldDesc.title())
                        data.put("dataIndex", field.name)
                        fieldList.add(data)
                    }
                }
                return R.okWithData(fieldList)
            default:
                return R.error("不支持的类型")


        }

    }


}
