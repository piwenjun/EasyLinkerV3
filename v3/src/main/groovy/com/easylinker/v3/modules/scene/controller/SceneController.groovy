package com.easylinker.v3.modules.scene.controller

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import com.easylinker.v3.modules.scene.form.AddSceneForm
import com.easylinker.v3.modules.scene.model.PreInstallTemplate
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.model.SceneType
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
 * 场景业务
 */
@RestController
@RequestMapping("/scene")
class SceneController extends AbstractController {
    @Autowired
    SceneService sceneService
    @Autowired
    TopicAclService topicAclService
    @Autowired
    DeviceService deviceService

    SceneController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 列出支持的场景类型
     * @return
     */
    @GetMapping("/listSceneType")
    R listSceneType() {

        List<Map<String, Object>> list = new ArrayList<>()
        for (SceneType sceneType : SceneType.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", sceneType.name)
            map.put("key", sceneType)
            list.add(map)
        }
        return R.okWithData(list)
    }
    /**
     * 列出系统内置的场景
     * @return
     */
    @GetMapping("/listPreInstallTemplate")

    R listPreInstallTemplate() {
        List<Map<String, Object>> list = new ArrayList<>()
        for (PreInstallTemplate preInstallTemplate : PreInstallTemplate.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", preInstallTemplate.name)
            map.put("key", preInstallTemplate)
            list.add(map)
        }
        return R.okWithData(list)
    }


    /**
     * 添加一个场景
     * @param addSceneForm
     * @return
     */

    @PostMapping("/add")
    R add(@Valid @RequestBody AddSceneForm addSceneForm) {

        switch (addSceneForm.sceneType) {
            case SceneType.CUSTOM:
                //新建一个类型
                Scene scene = new Scene(name: addSceneForm.name,
                        sceneType: SceneType.CUSTOM,
                        info: addSceneForm.info,
                        appUser: getCurrentUser())
                sceneService.save(scene)
                return R.ok()
            case SceneType.PRE_INSTALL_TEMPLATE:
                return handPreInstallTemplate(addSceneForm.preInstallTemplate)
            default: return R.error()

        }
    }

    /**
     * 模板中的数据类型设备全部是HTTP协议
     * @param addSceneForm
     * @param preInstallTemplate
     * @return
     */

    private R handPreInstallTemplate(PreInstallTemplate preInstallTemplate) {
        switch (preInstallTemplate) {
            case PreInstallTemplate.HUMIDITY_TEMPERATURE_TEMPLATE:
                Scene scene = new Scene(name: "温湿度监控中心模板",
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: "MQTT温湿度监控模块",
                        appUser: getCurrentUser())
                sceneService.save(scene)
                //默认创建10个设备
                for (int i = 0; i < 10; i++) {
                    HTTPDevice httpDevice = new HTTPDevice(name: "温湿度检测模块[${i}]",
                            info: "温湿度检测模块[${i}]",
                            deviceType: DeviceType.VALUE,
                            appUser: getCurrentUser(),
                            scene: scene,
                            deviceProtocol: DeviceProtocol.HTTP)
                    deviceService.addHttpDevice(httpDevice)
                }

                return R.ok()
            case PreInstallTemplate.GPS_TEMPLATE:
                /**
                 * GPS有2个坐标，默认是HTTP设备
                 */
                Scene scene = new Scene(name: "GPS场景模板",
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: "GPS场景模板",
                        appUser: getCurrentUser())
                sceneService.save(scene)
                //默认创建10个设备
                for (int i = 0; i < 10; i++) {
                    HTTPDevice httpDevice = new HTTPDevice(name: "GPS跟踪模块[${i}]",
                            info: "GPS跟踪模块[${i}]",
                            deviceType: DeviceType.VALUE,
                            appUser: getCurrentUser(),
                            scene: scene,
                            deviceProtocol: DeviceProtocol.HTTP)
                    deviceService.addHttpDevice(httpDevice)
                }

                return R.ok()

            case PreInstallTemplate.GENERAL_SWITCH_TEMPLATE:
                /**
                 * 通用开关是MQTT类型的设备
                 */

                Scene scene = new Scene(name: "通用开关模块模板",
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: "通用开关模块模板",
                        appUser: getCurrentUser())
                sceneService.save(scene)
                //默认创建10个设备
                for (int i = 0; i < 10; i++) {
                    MQTTDevice mqttDevice = new MQTTDevice(name: "通用开关模块模板[${i}]",
                            info: "通用开关模块模板[${i}]",
                            clientId: UUID.randomUUID().toString().replace("-", ""),
                            password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                            username: UUID.randomUUID().toString().replace("-", ""),
                            deviceType: DeviceType.VALUE,
                            appUser: getCurrentUser(),
                            scene: scene,
                            deviceProtocol: DeviceProtocol.MQTT)
                    deviceService.addMqttDevice(mqttDevice)

                }
                return R.ok()

            case PreInstallTemplate.SERIAL_DISPLAY_TEMPLATE:
                /**
                 * 串口屏是文本类型的设备
                 */
                Scene scene = new Scene(name: "通用串口显示屏",
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: "通用串口显示屏",
                        appUser: getCurrentUser())
                sceneService.save(scene)
                //默认创建10个设备
                for (int i = 0; i < 10; i++) {
                    MQTTDevice mqttDevice = new MQTTDevice(name: "通用串口显示屏[${i}]",
                            info: "通用串口显示屏[${i}]",
                            clientId: UUID.randomUUID().toString().replace("-", ""),
                            password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                            username: UUID.randomUUID().toString().replace("-", ""),
                            deviceType: DeviceType.TEXT,
                            appUser: getCurrentUser(),
                            scene: scene,
                            deviceProtocol: DeviceProtocol.MQTT)
                    deviceService.addMqttDevice(mqttDevice)
                }
                return R.ok()

            default:
                return R.error()
        }

    }

    /**
     * 列出场景
     * @param page
     * @param size
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/list")
    R list(@RequestParam int page, @RequestParam int size) {
        Page<Scene> scenePage = sceneService.findByAppUser(getCurrentUser(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return R.okWithData(scenePage)
    }
    /**
     * 列出场景下的设备
     * @param page
     * @param size
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/listDevice")
    R listDevice(@RequestParam int page,
                 @RequestParam int size,
                 @RequestParam DeviceProtocol deviceProtocol,
                 @RequestParam String sceneSecurityId
    ) {
        Scene scene = sceneService.findBySecurityId(sceneSecurityId)
        if (!scene) {
            return R.error()
        }

        Page<AbstractDevice> scenePage = deviceService.listDeviceByScene(scene, deviceProtocol, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        return R.okWithData(scenePage)
    }


}