package com.easylinker.coapservice.coapdata.controller

import com.alibaba.fastjson.JSONObject
import com.easylinker.coapservice.model.DeviceData
import com.mbed.coap.client.CoapClient
import com.mbed.coap.client.CoapClientBuilder
import com.mbed.coap.packet.CoapPacket
import com.mbed.coap.packet.MediaTypes

/**
 * 测试客户端【这货很坑不要用，用Python客户端测试】
 */
class CoAPClient {

    static void main(String[] args) throws InterruptedException {
        /**
         *{
         *     "data":"123",
         *     "info":"温度",
         *     "isDelete":false,
         *     "unit":"'C",
         *}*/

        DeviceData deviceData = new DeviceData(
                data: "123",
                unit: "'C",
                info: "温度"
        )
        CoapClient client = CoapClientBuilder.newBuilder(new InetSocketAddress("localhost", 5683)).build()

        for (int i = 0; i < 20; i++) {
            Thread.sleep(2)
            println "第${i}次POST请求"
            //d3f9c3f50a7947308926502e81363b1e
            CoapPacket coapResp = client.resource("/push").payload(JSONObject.toJSONString(deviceData), MediaTypes.CT_TEXT_PLAIN).sync().post()
            println "服务器回应:" + coapResp.toString(true)

        }
        //client.close()

    }
}
