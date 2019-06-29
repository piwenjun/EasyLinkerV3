package com.easylinker.v3.modules.message.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.Entity

/**
 * Created by admin on 2019/6/11 10:20
 *
 * 系统内部提示消息
 */

@Entity
class Message extends AbstractModel {

    /**
     * 消息类型
     */
    private String msgType

    /**
     * 消息触发者
     */
    private String producer

    /**
     * 消息内容
     */
    private String msgContent
    /**
     * 状态
     */
    private Integer state

    String getMsgType() {
        return msgType
    }

    void setMsgType(String msgType) {
        this.msgType = msgType
    }

    String getProducer() {
        return producer
    }

    void setProducer(String producer) {
        this.producer = producer
    }

    String getMsgContent() {
        return msgContent
    }

    void setMsgContent(String msgContent) {
        this.msgContent = msgContent
    }

    Integer getState() {
        return state
    }

    void setState(Integer state) {
        this.state = state
    }
}
