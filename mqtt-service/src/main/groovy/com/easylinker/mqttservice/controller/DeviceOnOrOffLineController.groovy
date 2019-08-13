package com.easylinker.mqttservice.controller

import com.easylinker.framework.common.model.DeviceLog
import com.easylinker.framework.common.web.R
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 设备上下线控制器
 */
@RestController
@RequestMapping("/onOffLine")
class DeviceOnOrOffLineController {
    Logger logger = LoggerFactory.getLogger(getClass())
    @Autowired
    JdbcTemplate jdbcTemplate
    @Autowired
    MongoTemplate mongoTemplate
    /**
     * 上线监控
     *{*   "anonymous": true,
     *   "auth_result": "success",
     *   "clean_start": true,
     *   "client_id": "c_emqx",
     *   "connack": 0,
     *   "connected_at": 1565617536853,
     *   "event": "client.connected",
     *   "is_bridge": false,
     *   "keepalive": 60,
     *   "mountpoint": "undefined",
     *   "node": "emqx@127.0.0.1",
     *   "peername": "127.0.0.1:63412",
     *   "proto_ver": 4,
     *   "sockname": "127.0.0.1:1883",
     *   "username": "u_emqx",
     *   "ws_cookie": "undefined",
     *   "zone": "external"
     *}* @param map
     * @return
     */
    @PostMapping("/on")
    R on(@RequestBody Map<String, Object> map) {

        String clientId = map.get("client_id") as String
        if (clientId) {
            try {
                Map<String, Object> deviceMap = jdbcTemplate.queryForMap("select security_id,client_id from `mqttdevice` where client_id = ?", clientId)
                if (deviceMap) {
                    jdbcTemplate.update("update `mqttdevice` set `online` = 1 where client_id = ?", deviceMap.get("client_id"))
                }


                DeviceLog deviceLog = new DeviceLog(deviceSecurityId: deviceMap.get("security_id"),
                        event: "ONLINE",
                        info: "设备[" + deviceMap.get("client_id") + "]上线",
                        createTime: new Date()
                )
                mongoTemplate.save(deviceLog, "DEVICE_LOG")
            } catch (e) {
                //e.printStackTrace()
                logger.error(e.message)
            }
        }
        return R.ok()
    }
    /**
     * 下线监控
     * @param map
     * @return
     */

    @PostMapping("/off")
    R off(@RequestBody Map<String, Object> map) {
        String clientId = map.get("client_id") as String
        if (clientId) {
            try {
                Map<String, Object> deviceMap = jdbcTemplate.queryForMap("select security_id,client_id from `mqttdevice` where client_id = ?", clientId)
                if (deviceMap) {
                    jdbcTemplate.update("update `mqttdevice` set `online` = 0 where client_id = ?", deviceMap.get("client_id"))
                }


                DeviceLog deviceLog = new DeviceLog(deviceSecurityId: deviceMap.get("security_id"),
                        event: "OFFLINE",
                        info: "设备[" + deviceMap.get("client_id") + "]离线",
                        createTime: new Date()
                )
                mongoTemplate.save(deviceLog, "DEVICE_LOG")
            } catch (e) {
                //e.printStackTrace()
                logger.error(e.message)
            }
        }
        return R.ok()

    }
}
