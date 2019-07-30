package com.easylinker.coapservice.coapdata.controller

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.Utils
import org.eclipse.californium.core.coap.MediaTypeRegistry

class CoAPClient {

    static void main(String[] args) throws InterruptedException {


        CoapClient client = new CoapClient()
        client.setURI("coap://localhost:5863/push?token=zq6NDc3sb6QmoQF1&data=112234")


        println("GET:")
        client.get(new CoapHandler() {
            @Override
            void onLoad(CoapResponse coapResponse) {
                println coapResponse.payload.toString()

            }

            @Override
            void onError() {
                println "error"

            }
        })
        println("POST:")

        client.post(new CoapHandler() {

            @Override
            void onLoad(CoapResponse response) {
                System.out.println(Utils.prettyPrint(response));
            }

            @Override
            void onError() {

            }
        }, "T:1,C:23", MediaTypeRegistry.TEXT_PLAIN);

    }
}
