package com.easylinker.mqttservice.controller

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.model.DeviceData
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.framework.utils.DeviceTokenUtils
import com.easylinker.framework.utils.RedisUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mqttData")
class DataController {
    Logger logger = LoggerFactory.getLogger(getClass())
    private static final String DATA_TABLE = "DEVICE_DATA"

    @Autowired
    MongoTemplate mongoTemplate
    /**
     * @return
     */
    @PostMapping("/post")
    R post(@RequestBody JSONObject mqttMessage) {
        logger.debug("设备:" + mqttMessage.get("client_id") + " 提交数据上来:" + mqttMessage.get("payload"))

        /**
         * 都是一些合法性判断
         */

        JSONObject dataJson
        String token
        try {
            dataJson = mqttMessage.getJSONObject("payload")

            token = dataJson.getString("token").toString()
            if (!token) {
                throw new XException("Invalid token!")
            }
        } catch (Exception e) {
            logger.error(e.message)
            return R.error("Invalid Token!")
        }
        /**
         * 频率限制 2S
         */
        if (requestIsLimit(token)) {
            return
        }

        /**
         * String token【必填】
         * String data 【必填】JSON格式！！！
         * String unit 【选填】
         * String info 【选填】
         */
        JSONObject data
        try {
            data = dataJson.getJSONObject("data")

        } catch (e) {
            e.printStackTrace()
            logger.error(e.message)

            return R.error("Missing [data] filed!")

        }
        String unit = dataJson.getString("unit")
        String info = dataJson.getString("info")

        JSONArray tokenList
        try {
            //[0cc33895a5cd46feb9483a02ca52c5d7, BOOLEAN.[]]
            tokenList = DeviceTokenUtils.getInfo((token))

        } catch (Exception e) {
            e.printStackTrace()
            logger.error(e.message)
            return R.error("Missing [token] filed!")

        }
        //非空处理
        if (tokenList.size() < 3) {
            return R.error("Token seems invalid!")

        }
        //全部过滤
        String deviceSecurityId = tokenList[0]
        String deviceType = tokenList[1]
        JSONArray dataFields = JSONObject.parseArray(tokenList[2].toString())
        //生成数据
        JSONObject realData = DeviceTokenUtils.xo(dataFields, data)
        if (realData.size() > 0) {
            DeviceData deviceData = new DeviceData(
                    deviceType: DeviceType.valueOf(deviceType),
                    securityId: UUID.randomUUID().toString().replace("-", ""),
                    createTime: new Date(),
                    updateTime: new Date(),
                    data: realData,
                    unit: unit ? "" : unit,
                    deviceSecurityId: deviceSecurityId,
                    info: info ? "" : info
            )
            //保存数据
            mongoTemplate.save(deviceData, DATA_TABLE)
            return R.ok("Post success!")
        } else {
            return R.error("Data not match fields!")
        }

    }
    @Autowired
    RedisUtils redisUtils

    private boolean requestIsLimit(String token) {
        try {
            String flag = redisUtils.get("MQTT_REQUEST_FREQUENCY:" + token)
            if (flag) {
                return true
            } else {
                redisUtils.set("MQTT_REQUEST_FREQUENCY:" + token, token, 2)
                return false
            }

        } catch (Exception e) {
            logger.error(e.message)
            //设置两秒过期时间
            redisUtils.set("MQTT_REQUEST_FREQUENCY:" + token, token, 2)
            return false
        }

    }
}
