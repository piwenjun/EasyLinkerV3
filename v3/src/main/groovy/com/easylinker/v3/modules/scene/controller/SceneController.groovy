package com.easylinker.v3.modules.scene.controller

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.modules.scene.form.AddSceneForm
import com.easylinker.v3.modules.scene.form.UpdateSceneForm
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
    R add(  @RequestBody AddSceneForm addSceneForm) {

        switch (addSceneForm.sceneType) {
            case SceneType.CUSTOM:
                //新建一个类型
                Scene scene = new Scene(name: addSceneForm.getName(),
                        sceneType: SceneType.CUSTOM,
                        info: addSceneForm.info,
                        appUser: getCurrentUser())
                sceneService.save(scene)
                return R.ok("场景创建成功")
            case SceneType.PRE_INSTALL_TEMPLATE:
                return handPreInstallTemplate(addSceneForm)
            default: return R.error("场景类型不支持")

        }
    }

    /**
     * 模板中的数据类型设备全部是HTTP协议
     * @param addSceneForm
     * @param preInstallTemplate
     * @return
     */

    private R handPreInstallTemplate(AddSceneForm addSceneForm) {
        switch (addSceneForm.preInstallTemplate) {
            case PreInstallTemplate.HUMIDITY_TEMPERATURE_TEMPLATE:
                Scene scene = new Scene(name: addSceneForm.getName(),
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: addSceneForm.info,
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
                    deviceService.create(httpDevice)
                }

                return R.ok("场景创建成功")
            case PreInstallTemplate.GPS_TEMPLATE:
                /**
                 * GPS有2个坐标，默认是HTTP设备
                 */
                Scene scene = new Scene(name: addSceneForm.getName(),
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: addSceneForm.info,
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
                    deviceService.create(httpDevice)
                }

                return R.ok("场景创建成功")

            case PreInstallTemplate.GENERAL_SWITCH_TEMPLATE:
                /**
                 * 通用开关是MQTT类型的设备
                 */

                Scene scene = new Scene(name: addSceneForm.getName(),
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: addSceneForm.info,
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
                    deviceService.create(mqttDevice)

                }
                return R.ok("场景创建成功")

            case PreInstallTemplate.SERIAL_DISPLAY_TEMPLATE:
                /**
                 * 串口屏是文本类型的设备
                 */
                Scene scene = new Scene(name: addSceneForm.getName(),
                        sceneType: SceneType.PRE_INSTALL_TEMPLATE,
                        info: addSceneForm.info,
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
                    deviceService.create(mqttDevice)
                }
                return R.ok("场景创建成功")

            default:
                return R.error("场景创建失败")
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
            return R.error("场景不存在")
        }

        Page<AbstractDevice> scenePage = deviceService.listDeviceBySceneSecurityId(sceneSecurityId, deviceProtocol, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")))
        return R.okWithData(scenePage)
    }

    /**
     * 更新
     * @param updateForm
     * @return
     */
    @PostMapping("/update")
    R update(@RequestBody UpdateSceneForm updateForm) {
        Scene scene = sceneService.findBySecurityId(updateForm.securityId)
        if (scene) {
            scene.setName(updateForm.getName())
            scene.setInfo(updateForm.info)
            sceneService.save(scene)
            return R.ok("更新成功")
        } else {
            return R.error("场景不存在")
        }

    }
}