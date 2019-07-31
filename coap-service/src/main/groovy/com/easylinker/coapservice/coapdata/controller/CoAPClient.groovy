package com.easylinker.coapservice.coapdata.controller

import com.mbed.coap.client.CoapClient
import com.mbed.coap.client.CoapClientBuilder
import com.mbed.coap.packet.CoapPacket

class CoAPClient {

    static void main(String[] args) throws InterruptedException {
        CoapClient client = CoapClientBuilder.newBuilder(new InetSocketAddress("localhost", 5683)).build()

        for (int i = 0; i < 20; i++) {
            Thread.sleep(2)
            CoapPacket coapResp = client.resource("/push") .sync().post().setPayload("hello")
            println(coapResp?.payloadString)
        }
        //client.close()
    }
}
