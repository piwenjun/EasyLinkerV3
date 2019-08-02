package com.easylinker.coapservice.config

import com.alibaba.fastjson.JSONObject
import com.easylinker.coapservice.model.DeviceData
import com.easylinker.framework.common.web.R
import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.packet.CoapPacket
import com.mbed.coap.packet.Code
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.server.CoapServer
import com.mbed.coap.utils.CoapResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

/**
 * @author wwhai* @date 2019/7/30 21:26
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class CoAPServerConfig {
    private Logger logger = LoggerFactory.getLogger(getClass())
    /**
     * MongoDB的缓存表名字
     *
     */
    private static final String CACHE_INFO_TABLE = "COAP_CATCH_INFO"
    private static final String MYSQL_QUERY_SQL = "select sn,token,security_id,device_type, from coapdevice where token = ?"

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    MongoTemplate mongoTemplate
    @Value(value = '${coap.server.hostname}')
    String hostname
    @Value(value = '${coap.server.port}')
    int port
    /**
     * 添加CoAP协议处理器:考虑到COAP设备可能通过Get来传数据，所以两种形式都支持【用GET来提交数据在HTTP里面是不推荐的】
     * COAP设备上传的数据，必须有以下几个字段:
     * 1 token:String
     * 2 data:JSON 长度小于1024KB
     */


    @Bean
    CoapServer coapServer() {
        final CoapServer server = CoapServer.builder() .transport(port).build()
        /**
         * 【重要】此接口需要限制频率，目前先限制为每个客户端的上传时间间隔为2秒;具体
         * 实现方法：push的时候，先redis.get(deviceSecurityId);如果没有拿到说明没有限制，数据放行;
         * 否则拦截并且:redis.set(deviceSecurityId,2s)
         * --------------------------------------------------------------------------------------------
         * 1 消息到来的时候 先检查【token】的合法性【这里需要调用V3的一个接口：/coap/isValid?token=[token]】
         *  [问题：一个请求会经过两次微服务，势必会影响性能，考虑如何优化这部分]
         * 2 如果【token】不合法，就不予处理
         * 3 如果【token】合法，保存数据到数据库
         * --------------------------------------------------------------------------------------------
         * 备注：关于这个Token的认证形式，初步先用最简单的数据库查找认证【但是真的很消耗性能】
         * 后面准备用非对称加密算法来认证
         * --------------------------------------------------------------------------------------------
         */

        server.addRequestHandler("/post", new CoapResource() {
            @Override
            void get(CoapExchange coapExchange) throws CoapCodeException {
                coapResponse(coapExchange, R.error("Only support post method on this version!"), Code.C405_METHOD_NOT_ALLOWED)
            }

            @Override
            void post(CoapExchange coapExchange) throws CoapCodeException {
                println("客户端POST:" + coapExchange.getRequestBodyString())


                //检查请求合法性
                if (!coapExchange.getRequestBodyString()) {
                    coapResponse(coapExchange, R.error("Error,Empty payload!"), Code.C400_BAD_REQUEST)
                }
                if (coapExchange.getRequestBodyString().length() > 1024) {
                    coapResponse(coapExchange, R.error("Data too long!Most 1024KB!"), Code.C400_BAD_REQUEST)

                } else {
                    JSONObject dataJson = new JSONObject()

                    try {
                        dataJson = JSONObject.parseObject(coapExchange.request.payloadString)
                    } catch (Exception e) {
                        coapResponse(coapExchange, R.error("Data format mast json!"), Code.C400_BAD_REQUEST)
                        logger.error(e.message)
                        return

                    }
                    //JSON 可能解析异常
                    //数据入库请求
                    String token
                    try {
                        token = dataJson.getString().toString()
                    } catch (Exception e) {
                        coapResponse(coapExchange, R.error("Invalid token!"), Code.C401_UNAUTHORIZED)
                        logger.error(e.message)

                        return
                    }
                    /**
                     * String token【必填】
                     * String data【必填】
                     * String unit【选填】
                     * String info【选填】
                     */
                    String data = dataJson.getString("token")
                    String unit = dataJson.getString("unit")
                    String info = dataJson.getString("info")

                    //请求【V3】保存数据
                    if ((!token)) {
                        coapResponse(coapExchange, R.error("Invalid token!"), Code.C401_UNAUTHORIZED)
                    }
                    if (!data) {
                        coapResponse(coapExchange, R.error("Invalid data!"), Code.C400_BAD_REQUEST)

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
                                unit: unit ? "" : unit,
                                deviceSecurityId: cacheInfo.get("security_id").toString(),
                                info: info ? "" : info
                        )
                        //保存设备数据到MongoDb
                        mongoTemplate.save(deviceData, "COAP_DATA")
                        coapResponse(coapExchange, R.ok("Post Success!!"), Code.C201_CREATED)
                        //
                    } else {
                        //还存不存在,查Mysql:[sn,token,security_id,device_type]
                        Map<String, Object> deviceMap
                        try {
                            deviceMap = jdbcTemplate.queryForMap(MYSQL_QUERY_SQL, token)
                        } catch (Exception ex) {
                            //ex.printStackTrace()
                            logger.error(ex.message)
                            //TODO
                            //这里会报一个空指针，但是不清楚啥原因
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
                        //保存数据
                        mongoTemplate.save(deviceData, "COAP_DATA")
                        //更新缓存
                        mongoTemplate.save(deviceMap, CACHE_INFO_TABLE)
                        coapResponse(coapExchange, R.ok("Post Success!!"), Code.C201_CREATED)

                    }

                }
            }
        })
        server.start()
        return server
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

    private static void coapResponse(CoapExchange coapExchange, R r, Code code) {
        CoapPacket coapPacket=new CoapPacket()
        coapPacket.setCode(code)
        coapPacket.setPayload(r.toJSONString())
        coapExchange.setResponse(coapPacket)
        coapExchange.sendResponse()
    }
}


