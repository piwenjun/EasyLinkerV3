package com.easylinker.coapservice.config

import com.alibaba.fastjson.JSONObject
import com.easylinker.coapservice.model.DeviceData
import com.easylinker.framework.common.web.R
import com.easylinker.framework.utils.RedisUtils
import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.packet.Code
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.utils.CoapResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

/**
 * CoAP设备数据处理器
 * @author wwhai* @date 2019/8/3 17:56
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class CacheTokenResource extends CoapResource {
    private Logger logger = LoggerFactory.getLogger(getClass())
    /**
     * MongoDB的缓存表名字
     *
     */
    private static final String CACHE_INFO_TABLE = "COAP_CACHE_INFO"
    private static final String COAP_DATA_TABLE = "COAP_DATA"

    private static final String MYSQL_QUERY_SQL = "select sn,token,security_id,device_type from coapdevice where token = ?"

    @Autowired
    JdbcTemplate jdbcTemplate
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
            JSONObject dataJson = new JSONObject()
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
            String data = dataJson.getString("data")
            String unit = dataJson.getString("unit")
            String info = dataJson.getString("info")

            //请求【V3】保存数据
            if ((!token)) {
                coapResponse(coapExchange, R.error("Invalid token!"), Code.C401_UNAUTHORIZED)
                return
            }
            if (!data) {
                coapResponse(coapExchange, R.error("Invalid data!"), Code.C400_BAD_REQUEST)
                return
            }

            /**
             * 缓存机制：
             * 1 每一次请求进来要进行安全过滤，判断token是否合法
             * 2 先从MongoDb查询这个token对应的设备的Sid，如果存在 提取sic后继续，保存数据
             * 3 如果不存在，去查Mysql，mysql没有，直接返回错误，如果有，把sid和token保存进mongodb缓存，然后保存数据
             */
            Query query = new Query()
            Criteria criteria = Criteria.where("token").is(token)
            query.addCriteria(criteria)
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "id")))
            //查找缓存
            Map<String, Object> cacheInfo = mongoTemplate.findOne(query, HashMap.class, CACHE_INFO_TABLE)
            if (cacheInfo) {
                //如果缓存里面有设备信息，第一步就可以100%确认设备合法
                DeviceData deviceData = new DeviceData(
                        dataType: cacheInfo.get("device_type").toString(),
                        securityId: UUID.randomUUID().toString().replace("-", ""),
                        createTime: new Date(),
                        updateTime: new Date(),
                        data: data,
                        unit: unit ? "-" : unit,
                        deviceSecurityId: cacheInfo.get("security_id").toString(),
                        info: info ? "-" : info
                )
                // println ("有缓存了")

                //保存设备数据到MongoDb
                mongoTemplate.save(deviceData, COAP_DATA_TABLE)
                coapResponse(coapExchange, R.ok("Post Success!"), Code.C201_CREATED)
                //
            } else {
                //还存不存在,查Mysql:[sn,token,security_id,device_type]
                Map<String, Object> deviceMap
                try {
                    deviceMap = jdbcTemplate.queryForMap(MYSQL_QUERY_SQL, token)
                } catch (Exception ex) {
                    //ex.printStackTrace()
                    logger.error(ex.message)
                    coapResponse(coapExchange, R.error("Device not exist!"), Code.C400_BAD_REQUEST)
                    return

                }
                DeviceData deviceData = new DeviceData(
                        dataType: deviceMap.get("device_type").toString(),
                        securityId: UUID.randomUUID().toString().replace("-", ""),
                        createTime: new Date(),
                        updateTime: new Date(),
                        data: data,
                        unit: unit ? "" : unit,
                        deviceSecurityId: deviceMap.get("security_id").toString(),
                        info: info ? "" : info
                )
                // println ("第一次必定走这一步")
                //保存数据
                mongoTemplate.save(deviceData, COAP_DATA_TABLE)
                //更新缓存
                //TODO:这里有个重要的细节，后面版本更新的时候完善：删除设备的时候，缓存也要刷新干净，不然会导致冗余数据
                //TODO：后台会跑一个定时任务，每天定时清空缓存表【COAP_CACHE_TABLE】
                mongoTemplate.save(deviceMap, CACHE_INFO_TABLE)
                //返回提示信息
                coapResponse(coapExchange, R.ok("Post Success!"), Code.C201_CREATED)
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
