package com.easylinker.v3.modules.device.controller

import cn.hutool.crypto.digest.DigestUtil
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceStatus
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.v3.modules.device.form.*
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.device.service.TopicAclService
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.scene.service.SceneService
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

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
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
    R addCoAP(@RequestBody @Valid CoAPDeviceForm coAPDeviceForm) {
        if (coAPDeviceForm.sceneSecurityId) {
            Scene scene = sceneService.findBySecurityId(coAPDeviceForm.sceneSecurityId)
            if (scene) {

                CoAPDevice CoAPDevice = new CoAPDevice(name: coAPDeviceForm.name,
                        info: coAPDeviceForm.info,
                        token: UUID.randomUUID().toString().replace("-", ""),
                        deviceType: coAPDeviceForm.deviceType,
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
                    info: coAPDeviceForm.info,
                    token: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: coAPDeviceForm.deviceType,
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

                deviceService.add(mqttDevice)
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
            deviceService.add(mqttDevice)
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

                deviceService.add(terminalHostDevice)
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
            deviceService.add(terminalHostDevice)
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
                TCPDevice tcpDevice = new TCPDevice(name: tcpDeviceForm.name,
                        info: tcpDeviceForm.info,
                        token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        username: UUID.randomUUID().toString().replace("-", ""),
                        deviceType: tcpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.TCP)

                deviceService.add(tcpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            TCPDevice tcpDevice = new TCPDevice(name: tcpDeviceForm.name,
                    info: tcpDeviceForm.info,
                    token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    password: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    username: UUID.randomUUID().toString().replace("-", ""),
                    deviceType: tcpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.TCP)
            deviceService.add(tcpDevice)
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
                UDPDevice udpDevice = new UDPDevice(name: udpDeviceForm.name,
                        info: udpDeviceForm.info,
                        token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                        deviceType: udpDeviceForm.deviceType,
                        appUser: getCurrentUser(),
                        scene: scene,
                        deviceProtocol: DeviceProtocol.UDP)

                deviceService.add(udpDevice)
                return R.ok("添加成功")

            } else {
                return R.error("场景不存在")

            }

        } else {
            UDPDevice udpDevice = new UDPDevice(name: udpDeviceForm.name,
                    info: udpDeviceForm.info,
                    token: DigestUtil.sha256Hex(UUID.randomUUID().toString()),
                    deviceType: udpDeviceForm.deviceType,
                    appUser: getCurrentUser(),
                    deviceProtocol: DeviceProtocol.UDP)
            deviceService.add(udpDevice)
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
    R update(@RequestBody UpdateForm updateForm) {

        AbstractDevice abstractDevice = deviceService.detail(updateForm.securityId, updateForm.deviceProtocol)
        Scene scene = sceneService.findBySecurityId(updateForm.sceneSecurityId)
        if (abstractDevice) {
            AbstractDevice device
            switch (updateForm.deviceProtocol) {
                case DeviceProtocol.MQTT:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.name)
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.CoAP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.name)
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.HTTP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.name)
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.TCP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.name)
                    device.setInfo(updateForm.info)
                    if (scene)
                        device.setScene(scene)
                    deviceService.save(device)
                    return R.ok("更新成功")

                case DeviceProtocol.UDP:
                    device = abstractDevice as MQTTDevice
                    device.setName(updateForm.name)
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
}
