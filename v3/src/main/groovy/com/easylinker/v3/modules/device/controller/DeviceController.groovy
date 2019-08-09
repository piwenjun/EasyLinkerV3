package com.easylinker.v3.modules.device.controller

import cn.hutool.crypto.digest.DigestUtil
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.web.R
import com.easylinker.framework.utils.DeviceTokenUtils
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceStatus
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.modules.device.form.*
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.device.service.DeviceDataFieldConfigService
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    DeviceDataFieldConfigService deviceDataFieldConfigService

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 获取字段
     * @param deviceSecurityId
     * @return
     */
    @GetMapping("/getFieldConfig")
    R getFieldConfig(@RequestParam String deviceSecurityId) {
        R.okWithData(deviceDataFieldConfigService.getByDevice(deviceSecurityId))

    }

    /**
     * 配置数据字段
     * @param deviceFieldConfigForm
     * @return
     */
    @PostMapping("/setFieldConfig")
    R setFieldConfig(@RequestBody @Valid DeviceFieldConfigForm deviceFieldConfigForm) {
        DeviceDataFieldConfig deviceDataFieldConfig = deviceDataFieldConfigService.getByDevice(deviceFieldConfigForm.deviceSecurityId)
        if (deviceDataFieldConfig) {
            deviceDataFieldConfig.setFields(JSONObject.toJSONString(deviceFieldConfigForm.fields))
            deviceDataFieldConfigService.save(deviceDataFieldConfig)
            return R.ok("配置成功")
        } else {
            return R.error("配置不存在")
        }
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

                HTTPDevice httpDevice = new HTTPDevice(
                        name: httpDeviceForm.getName(),
                        info: httpDeviceForm.info,
                        deviceType: httpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.HTTP)
                //token
                httpDevice.setToken(DeviceTokenUtils.token([
                        httpDevice.securityId,
                        httpDevice.deviceType
                ].toString()))
                deviceService.create(httpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")
            }

        } else {

            HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.getName(),
                    info: httpDeviceForm.info,
                    deviceType: httpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    deviceProtocol: DeviceProtocol.HTTP)
            httpDevice.setToken(DeviceTokenUtils.token(httpDevice.securityId))
            //token
            httpDevice.setToken(DeviceTokenUtils.token([
                    httpDevice.securityId,
                    httpDevice.deviceType
            ].toString()))
            deviceService.create(httpDevice)
            return R.ok("添加成功")


        }

    }
    /**
     * 添加CoAP设备：
     * 【8-3更新】token生成规则：token是用设备的SID，TYPE生成的字符串
     * @param CoAPDeviceForm
     * @return
     */

    @PostMapping("/addCoAP")
    R addCoAP(@RequestBody @Valid CoAPDeviceForm coAPDeviceForm) {
        if (coAPDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(coAPDeviceForm.sceneSecurityId)
            if (scene) {

                CoAPDevice coAPDevice = new CoAPDevice(name: coAPDeviceForm.name,
                        info: coAPDeviceForm.info,
                        deviceType: coAPDeviceForm.deviceType,
                        scene: scene,
                        appUser: getCurrentUser(),
                        deviceProtocol: DeviceProtocol.CoAP)
                //token
                String token = DeviceTokenUtils.token([coAPDevice.securityId, coAPDevice.deviceType].toString())
                coAPDevice.setToken(token)
                deviceService.create(coAPDevice)
                return R.ok("添加成功")
            } else {
                return R.error("场景不存在")

            }

        } else {

            CoAPDevice coAPDevice = new CoAPDevice(
                    name: coAPDeviceForm.getName(),
                    info: coAPDeviceForm.info,
                    deviceType: coAPDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.CoAP)
            //token
            coAPDevice.setToken(DeviceTokenUtils.token([coAPDevice.securityId, coAPDevice.deviceType].toString()))
            deviceService.create(coAPDevice)
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
                MQTTDevice mqttDevice = new MQTTDevice(name: mqttDeviceForm.getName(),
                        info: mqttDeviceForm.info,
                        clientId: UUID.randomUUID().toString().replace("-", ""),
                        password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        username: UUID.randomUUID().toString().replace("-", ""),
                        deviceType: mqttDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.MQTT)
                //token
                mqttDevice.setToken(DeviceTokenUtils.token([
                        mqttDevice.securityId,
                        mqttDevice.deviceType
                ].toString()))
                deviceService.create(mqttDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            MQTTDevice mqttDevice = new MQTTDevice(name: mqttDeviceForm.getName(),
                    info: mqttDeviceForm.info,
                    clientId: UUID.randomUUID().toString().replace("-", ""),
                    password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    username: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: mqttDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.MQTT)
            //token
            mqttDevice.setToken(DeviceTokenUtils.token([
                    mqttDevice.securityId,
                    mqttDevice.deviceType
            ].toString()))
            deviceService.create(mqttDevice)
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
                TerminalHostDevice terminalHostDevice = new TerminalHostDevice(name: terminalHostDeviceForm.getName(),
                        info: terminalHostDeviceForm.info,
                        sshUsername: terminalHostDeviceForm.sshUsername,
                        sshPassword: terminalHostDeviceForm.sshPassword,
                        sshPort: terminalHostDeviceForm.sshPort,
                        deviceType: terminalHostDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.TCP)

                deviceService.create(terminalHostDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }
        } else {
            TerminalHostDevice terminalHostDevice = new TerminalHostDevice(name: terminalHostDeviceForm.getName(),
                    info: terminalHostDeviceForm.info,
                    sshUsername: terminalHostDeviceForm.sshUsername,
                    sshPassword: terminalHostDeviceForm.sshPassword,
                    sshPort: terminalHostDeviceForm.sshPort,
                    deviceType: terminalHostDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.TCP)
            deviceService.create(terminalHostDevice)
            return R.ok("添加成功")
        }


    }

/**
 * 添加TCP
 * @param tcpDeviceForm
 * @return
 */
    @PostMapping("/addTCP")
    @Transactional(rollbackFor = Exception.class)
    R addTCP(@RequestBody @Valid TCPDeviceForm tcpDeviceForm) {

        if (tcpDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(tcpDeviceForm.sceneSecurityId)
            if (scene) {
                TCPDevice tcpDevice = new TCPDevice(name: tcpDeviceForm.getName(),
                        info: tcpDeviceForm.info,
                        password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        username: UUID.randomUUID().toString().replace("-", ""),
                        deviceType: tcpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.TCP)
                //token
                tcpDevice.setToken(DeviceTokenUtils.token([tcpDevice.securityId, tcpDevice.deviceType].toString()))
                deviceService.create(tcpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            TCPDevice tcpDevice = new TCPDevice(name: tcpDeviceForm.getName(),
                    info: tcpDeviceForm.info,
                    password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    username: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: tcpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.TCP)
            //token
            tcpDevice.setToken(DeviceTokenUtils.token([tcpDevice.securityId, tcpDevice.deviceType].toString()))
            deviceService.create(tcpDevice)
            return R.ok("添加成功")
        }


    }
/**
 * 添加UDP
 * @param udpDeviceForm
 * @return
 */
    @PostMapping("/addUDP")
    @Transactional(rollbackFor = Exception.class)
    R addUDP(@RequestBody @Valid UDPDeviceForm udpDeviceForm) {

        if (udpDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(udpDeviceForm.sceneSecurityId)
            if (scene) {
                UDPDevice udpDevice = new UDPDevice(name: udpDeviceForm.getName(),
                        info: udpDeviceForm.info,
                        deviceType: udpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.UDP)
                udpDevice.setToken(DeviceTokenUtils.token([udpDevice.securityId, udpDevice.deviceType].toString()))
                deviceService.create(udpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            UDPDevice udpDevice = new UDPDevice(name: udpDeviceForm.getName(),
                    info: udpDeviceForm.info,
                    deviceType: udpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.UDP)
            //token
            udpDevice.setToken(DeviceTokenUtils.token([udpDevice.securityId, udpDevice.deviceType].toString()))
            deviceService.create(udpDevice)
            return R.ok("添加成功")
        }


    }

/**
 * TODO: 此处不能直接返回enum的值，需要数据库进行配置
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
 * TODO: 此处不能直接返回enum的值，需要数据库进行配置
 * 获取支持的设备类型
 * @return
 */
    @GetMapping("/listDeviceStatus")

    R listDeviceStatus() {

        List<Map<String, Object>> list = new ArrayList<>()
        for (DeviceStatus deviceStatus : DeviceStatus.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", deviceStatus.title)
            map.put("key", deviceStatus)
            list.add(map)
        }
        return R.okWithData(list)
    }

/**
 * TODO: 此处不能直接返回enum的值，需要数据库进行配置
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
        AbstractDevice abstractDevice = deviceService.detail(securityId, deviceProtocol)
        if (abstractDevice) {
            return R.okWithData(abstractDevice)

        } else {
            return R.error("设备不存在")
        }

    }


/**
 * 获取设备列表[7-16最新版，上面的是最初写的，后来发现不利于动态扩展于是写了个泛型查询]
 * @param page
 * @param size
 * @param deviceProtocol
 * @return
 */
    @GetMapping("/listByProtocol")
    R listDeviceByProtocol(@RequestParam int page, @RequestParam int size, @RequestParam DeviceProtocol deviceProtocol) {
        return R.okWithData(deviceService.listByProtocol(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")), getCurrentUser(), deviceProtocol))

    }
/**
 * 根据设备类型筛选
 * @param page
 * @param size
 * @param deviceType
 * @return
 */

    @GetMapping("/listByType")
    R listDeviceByType(@RequestParam int page, @RequestParam int size, @RequestParam DeviceType deviceType) {
        return R.okWithData(deviceService.listByType(getCurrentUser(), deviceType, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

    }

/**
 * 搜索
 * @param page
 * @param size
 * @param deviceProtocol
 * @param deviceType
 * @param deviceStatus
 * @param name
 * @param info
 * @param sn
 * @param sceneSecurityId
 * @return
 */

    @Autowired
    UserService userService

    @GetMapping("/list")
    R list(@RequestParam int page,
           @RequestParam int size,
           @RequestParam DeviceProtocol deviceProtocol,
           @RequestParam(required = false) DeviceType deviceType,
           @RequestParam(required = false) DeviceStatus deviceStatus,
           @RequestParam(required = false) String name,
           @RequestParam(required = false) String info,
           @RequestParam(required = false) String sn,
           @RequestParam(required = false) String sceneSecurityId) {
        Scene scene = sceneService.findBySecurityId(sceneSecurityId)
        AppUser appUser = userService.findBySecurityId(getCurrentUser().securityId)

        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return R.okWithData(deviceService.search(new MQTTDevice(
                        appUser: appUser,
                        scene: scene,
                        deviceProtocol: deviceProtocol,
                        deviceType: deviceType,
                        deviceStatus: deviceStatus,
                        name: name,
                        info: info,
                        sn: sn), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.TCP:
                return R.okWithData(deviceService.search(new TCPDevice(
                        appUser: appUser,
                        scene: scene,
                        deviceProtocol: deviceProtocol,
                        deviceType: deviceType,
                        deviceStatus: deviceStatus,
                        name: name,
                        info: info,
                        sn: sn), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.UDP:
                return R.okWithData(deviceService.search(new UDPDevice(
                        appUser: appUser,
                        scene: scene,
                        deviceProtocol: deviceProtocol,
                        deviceType: deviceType,
                        deviceStatus: deviceStatus,
                        name: name,
                        info: info,
                        sn: sn), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.HTTP:
                return R.okWithData(deviceService.search(new HTTPDevice(
                        appUser: appUser,
                        scene: scene,
                        deviceProtocol: deviceProtocol,
                        deviceType: deviceType,
                        deviceStatus: deviceStatus,
                        name: name,
                        info: info,
                        sn: sn), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            case DeviceProtocol.CoAP:
                return R.okWithData(deviceService.search(new CoAPDevice(
                        appUser: appUser,
                        scene: scene,
                        deviceProtocol: deviceProtocol,
                        deviceType: deviceType,
                        deviceStatus: deviceStatus,
                        name: name,
                        info: info,
                        sn: sn), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))

            default:
                return R.okWithData([])


        }

    }


/**
 * 更新单个设备
 * @param name
 * @param info
 * @param sn
 * @param sceneSecurityId
 * @return
 */
    @PostMapping("/update")
    R update(@RequestBody @Valid UpdateForm updateForm) {

        AbstractDevice abstractDevice = deviceService.detail(updateForm.securityId, updateForm.deviceProtocol)
        Scene scene = sceneService.findBySecurityId(updateForm.sceneSecurityId)
        if (abstractDevice) {
            AbstractDevice device
            switch (updateForm.deviceProtocol) {
                case DeviceProtocol.MQTT:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.getName())
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.CoAP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.getName())
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.HTTP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.getName())
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.TCP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.getName())
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.UDP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.getName())
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                default: return R.error("更新失败")


            }
        } else {
            return R.error("设备不存在")
        }


    }

/**
 * 绑定设备到用户
 * @return
 */
    @PostMapping("/bind")
    R bind(@RequestBody BindForm bindForm) {
        AbstractDevice abstractDevice = deviceService.detail(bindForm.deviceSecurityId, bindForm.deviceProtocol)
        Scene scene = sceneService.findBySecurityId(bindForm.sceneSecurityId)

        if (abstractDevice) {
            switch (abstractDevice.class) {
                case MQTTDevice.class:
                    MQTTDevice device = abstractDevice as MQTTDevice
                    device.setAppUser(getCurrentUser())
                    if (scene) {
                        device.setScene(scene)
                    }
                    deviceService.save(device)
                    return R.ok("绑定成功")
                case HTTPDevice.class:
                    HTTPDevice device = abstractDevice as HTTPDevice
                    device.setAppUser(getCurrentUser())
                    if (scene) {
                        device.setScene(scene)
                    }
                    deviceService.save(device)
                    return R.ok("绑定成功")
                case CoAPDevice.class:
                    CoAPDevice device = abstractDevice as CoAPDevice
                    device.setAppUser(getCurrentUser())
                    if (scene) {
                        device.setScene(scene)
                    }
                    deviceService.save(device)
                    return R.ok("绑定成功")
                case TCPDevice.class:
                    TCPDevice device = abstractDevice as TCPDevice
                    device.setAppUser(getCurrentUser())
                    if (scene) {
                        device.setScene(scene)
                    }
                    deviceService.save(device)
                    return R.ok("绑定成功")
                case UDPDevice.class:
                    UDPDevice device = abstractDevice as UDPDevice
                    device.setAppUser(getCurrentUser())
                    if (scene) {
                        device.setScene(scene)
                    }
                    deviceService.save(device)
                    return R.ok("绑定成功")
                default: return R.error("该设备不存在")
            }

        } else {
            return R.error("该设备不存在")
        }

    }

/**
 * 获取ACL
 * @param mqttSecurityId
 * @return
 */
    @GetMapping("/listAcls")
    R listAcls(@RequestParam String mqttSecurityId) {
        AbstractDevice mqttDevice = deviceService.detail(mqttSecurityId, DeviceProtocol.MQTT) as MQTTDevice
        if (mqttDevice) {
            return R.okWithData(topicAclService.listAcls(mqttDevice))
        } else {
            return R.error("终端不存在")
        }
    }


}
