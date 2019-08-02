package com.easylinker.coapservice.coapdata.controller

import com.alibaba.fastjson.JSONObject
import com.easylinker.coapservice.model.DeviceData
import com.mbed.coap.client.CoapClient
import com.mbed.coap.client.CoapClientBuilder
import com.mbed.coap.packet.CoapPacket
import com.mbed.coap.packet.MediaTypes

class CoAPClient {

    static void main(String[] args) throws InterruptedException {
        /**
         *{*     "createTime":1564732582469,
         *     "data":"123",
         *     "dataType":"VALVE",
         *     "deviceSecurityId":"5051744f357f4cfa8f9689019716100a",
         *     "info":"温度",
         *     "isDelete":false,
         *     "securityId":"b9793949fd00414ea52768312eafe9f3",
         *     "unit":"'C",
         *     "updateTime":1564732582469
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
