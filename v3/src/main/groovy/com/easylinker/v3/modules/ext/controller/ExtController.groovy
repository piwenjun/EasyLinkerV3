package com.easylinker.v3.modules.ext.controller

import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.framework.common.web.ReturnEnum
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 一些辅助功能
 * @author wwhai* @date 2019/7/13 11:50
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/sysParam")
class ExtController {
    /**
     * 获取支持的设备类型
     * @return
     */
    @GetMapping("/listCode")
    R listCode() {
        List<Map<String, Object>> list = new ArrayList<>()
        for (ReturnEnum returnEnum : ReturnEnum.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("code", returnEnum.code)
            map.put("value", returnEnum)
            map.put("msg", returnEnum.message)
            list.add(map)
        }
        return R.okWithData(list)
    }

    /**
     * 通过设备类型获取协议
     * 目前是写死的 后面通过数据库配置
     * @return
     */

    @GetMapping("/getProtocolByType")
    R getProtocolByType(@RequestParam DeviceType deviceType) {
        List<HashMap<String, Object>> protocolList = new ArrayList<>()
        switch (deviceType) {
            case DeviceType.VALUE:
                protocolList.add([key: DeviceProtocol.MQTT, name: DeviceProtocol.MQTT.getName()])
                protocolList.add([key: DeviceProtocol.TCP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.UDP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.HTTP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.CoAP, name: DeviceProtocol.TCP.getName()])
                break
            case DeviceType.TEXT:
                protocolList.add([key: DeviceProtocol.MQTT, name: DeviceProtocol.MQTT.getName()])
                protocolList.add([key: DeviceProtocol.TCP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.UDP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.HTTP, name: DeviceProtocol.TCP.getName()])
                protocolList.add([key: DeviceProtocol.CoAP, name: DeviceProtocol.TCP.getName()])
                break
            case DeviceType.BOOLEAN:
                protocolList.add([key: DeviceProtocol.MQTT, name: DeviceProtocol.MQTT.getName()])
                protocolList.add([key: DeviceProtocol.TCP, name: DeviceProtocol.TCP.getName()])
                break
            case DeviceType.SWITCH:
                protocolList.add([key: DeviceProtocol.MQTT, name: DeviceProtocol.MQTT.getName()])
                protocolList.add([key: DeviceProtocol.TCP, name: DeviceProtocol.TCP.getName()])
                break
            case DeviceType.TERMINAL_HOST:
                protocolList.add([key: DeviceProtocol.MQTT, name: DeviceProtocol.MQTT.getName()])
                break
            case DeviceType.FILE:
                protocolList.add([key: DeviceProtocol.HTTP, name: DeviceProtocol.HTTP.getName()])
                break
        }

        return R.okWithData(protocolList)


    }


}
