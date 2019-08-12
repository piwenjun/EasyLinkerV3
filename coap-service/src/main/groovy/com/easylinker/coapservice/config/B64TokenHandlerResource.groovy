package com.easylinker.coapservice.config

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.easylinker.coapservice.model.DeviceData
import com.easylinker.framework.common.web.R
import com.easylinker.framework.utils.DeviceTokenUtils
import com.easylinker.framework.utils.RedisUtils
import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.packet.Code
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.utils.CoapResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

/**
 * [8-3日新增]Token认证通过Base64和私钥形式
 * B64字符串包含了设备的SID和TYPE
 * @author wwhai* @date 2019/8/3 18:04
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class B64TokenHandlerResource extends CoapResource {
    private Logger logger = LoggerFactory.getLogger(getClass())
    /**
     * COAP 设备数据保存表
     */
    private static final String COAP_DATA_TABLE = "DEVICE_DATA"


    @Autowired
    MongoTemplate mongoTemplate

    @Override
    void get(CoapExchange coapExchange) throws CoapCodeException {
        coapResponse(coapExchange, R.error("Only support post method on this version!"), Code.C405_METHOD_NOT_ALLOWED)
    }

    @Override
    void post(CoapExchange coapExchange) throws CoapCodeException {
        //println("客户端POST:" + coapExchange.getRequestBodyString())
        //检查请求合法性

        if (!coapExchange.getRequestBodyString()) {
            coapResponse(coapExchange, R.error("Error,Empty payload!"), Code.C400_BAD_REQUEST)
            return
        }
        if (coapExchange.getRequestBodyString().length() > 1024) {
            coapResponse(coapExchange, R.error("Data too long!Most 1024KB!"), Code.C400_BAD_REQUEST)

        } else {
            JSONObject dataJson
            /**
             * 都是一些合法性判断
             */
            try {
                dataJson = JSONObject.parseObject(coapExchange.request.payloadString)
            } catch (Exception e) {
                coapResponse(coapExchange, R.error("Data format mast json!"), Code.C400_BAD_REQUEST)
                logger.error(e.message)
                return

            }
            String token
            try {
                token = dataJson.getString("token").toString()
            } catch (Exception e) {
                coapResponse(coapExchange, R.error("Invalid token!"), Code.C401_UNAUTHORIZED)
                logger.error(e.message)

                return
            }
            /**
             * 频率限制 2S
             */
            if (requestIsLimit(token)) {
                coapResponse(coapExchange, R.error("High frequency request!"), Code.C502_BAD_GATEWAY)
                return
            }

            /**
             * String token【必填】
             * String data 【必填】
             * String unit 【选填】
             * String info 【选填】
             */
            JSONObject data
            try {
                data = dataJson.getJSONObject("data")

            } catch (e) {
                e.printStackTrace()

                coapResponse(coapExchange, R.error("Invalid data!"), Code.C400_BAD_REQUEST)
                return
            }
            String unit = dataJson.getString("unit")
            String info = dataJson.getString("info")

            if ((!token)) {
                coapResponse(coapExchange, R.error("Invalid token!"), Code.C401_UNAUTHORIZED)
                return
            }
            if (!data) {
                coapResponse(coapExchange, R.error("Invalid data!"), Code.C400_BAD_REQUEST)
                return
            }
            JSONArray tokenList
            try {
                //[0cc33895a5cd46feb9483a02ca52c5d7, BOOLEAN]
                tokenList = DeviceTokenUtils.getInfo((token))

            } catch (Exception e) {
                e.printStackTrace()
                logger.error(e.message)
                coapResponse(coapExchange, R.error("Invalid token!"), Code.C400_BAD_REQUEST)
                return
            }
            //非空处理
            if (tokenList.size() < 3) {
                coapResponse(coapExchange, R.error("Invalid token!"), Code.C400_BAD_REQUEST)
                return
            }
            //全部过滤
            String deviceSecurityId = tokenList[0]
            String deviceType = tokenList[1]
            JSONArray dataFields = JSONObject.parseArray(tokenList[2].toString())
            //生成数据
            JSONObject realData = DeviceTokenUtils.xo(dataFields, data)
            if (realData.size() > 0) {
                DeviceData deviceData = new DeviceData(
                        dataType: deviceType,
                        securityId: UUID.randomUUID().toString().replace("-", ""),
                        createTime: new Date(),
                        updateTime: new Date(),
                        data: realData,
                        unit: unit ? "" : unit,
                        deviceSecurityId: deviceSecurityId,
                        info: info ? "" : info
                )
                //保存数据
                mongoTemplate.save(deviceData, COAP_DATA_TABLE)
                coapResponse(coapExchange, R.error("Post success!"), Code.C201_CREATED)
            } else {
                coapResponse(coapExchange, R.error("Data not match fields!"), Code.C201_CREATED)
            }

        }
    }
    /**
     * 返回给客户端内容
     *     C201_CREATED(2, 1, 201),
     *     C202_DELETED(2, 2, 200),
     *     C203_VALID(2, 3, 200),
     *     C204_CHANGED(2, 4, 200),
     *     C205_CONTENT(2, 5, 200),
     *     C400_BAD_REQUEST(4, 0, 400),
     *     C401_UNAUTHORIZED(4, 1, 403),
     *     C402_BAD_OPTION(4, 2, 400),
     *     C403_FORBIDDEN(4, 3, 403),
     *     C404_NOT_FOUND(4, 4, 404),
     *     C405_METHOD_NOT_ALLOWED(4, 5, 400),
     *     C406_NOT_ACCEPTABLE(4, 6, 406),
     *     C412_PRECONDITION_FAILED(4, 12, 412),
     *     C415_UNSUPPORTED_MEDIA_TYPE(4, 15, 415),
     *     C500_INTERNAL_SERVER_ERROR(5, 0, 500),
     *     C501_NOT_IMPLEMENTED(5, 1, 501),
     *     C502_BAD_GATEWAY(5, 2, 502),
     *     C503_SERVICE_UNAVAILABLE(5, 3, 503),
     *     C504_GATEWAY_TIMEOUT(5, 4, 504),
     *     C505_PROXYING_NOT_SUPPORTED(5, 5, 502),
     *     C231_CONTINUE(2, 31, 500),
     *     C408_REQUEST_ENTITY_INCOMPLETE(4, 8, 500),
     *     C413_REQUEST_ENTITY_TOO_LARGE(4, 13, 413),
     *     C701_CSM(7, 1, 500),
     *     C702_PING(7, 2, 500),
     *     C703_PONG(7, 3, 500),
     *     C704_RELEASE(7, 4, 500),
     *     C705_ABORT(7, 5, 500);
     * @param response
     */

    private static void coapResponse(final CoapExchange coapExchange, R r, Code code) {
        coapExchange.setResponseCode(code)
        coapExchange.setResponseBody(r.toJSONString())
        coapExchange.sendResponse()
    }

    /**
     * 检查请求频率【每一个设备最少2秒间隔频率】否则会被拦截{自己可以根据需求调整}* 【重要】此接口需要限制频率，目前先限制为每个客户端的上传时间间隔为2秒;具体
     * 实现方法：push的时候，先redis.get(deviceSecurityId);如果没有拿到说明没有限制，数据放行;
     * 否则拦截并且:redis.set(deviceSecurityId,2s)
     * @param token
     */
    @Autowired
    RedisUtils redisUtils

    private boolean requestIsLimit(String token) {
        try {
            String flag = redisUtils.get("COAP_REQUEST_FREQUENCY:" + token)
            if (flag) {
                return true
            } else {
                redisUtils.set("COAP_REQUEST_FREQUENCY:" + token, token, 2)
                return false
            }

        } catch (Exception e) {
            logger.error(e.message)
            //设置两秒过期时间
            redisUtils.set("COAP_REQUEST_FREQUENCY:" + token, token, 2)
            return false
        }

    }
}
