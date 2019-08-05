package com.easylinker.mqttservice.config.mqttclient.bean;

import cn.hutool.json.JSONObject;

/**
 * 发布的消息封装
 */

public class PublishBean {
    private String topic;
    private boolean mutable = true;
    private JSONObject payload;
    private int qos = 2;
    private boolean retained = false;
    private boolean dup = false;

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public boolean isRetained() {
        return retained;
    }

    public void setRetained(boolean retained) {
        this.retained = retained;
    }

    public boolean isDup() {
        return dup;
    }

    public void setDup(boolean dup) {
        this.dup = dup;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
