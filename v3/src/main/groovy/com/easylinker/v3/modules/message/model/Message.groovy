package com.easylinker.v3.modules.message.model


import com.easylinker.v3.common.model.AbstractModel

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * Created by admin on 2019/6/11 10:20
 *
 * 系统内部提示消息【邮件】
 */

@Entity
class Message extends AbstractModel {

    /**
     * 用户的SecurityId
     */
    private String userSecurityId
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
    @Enumerated(EnumType.STRING)
    private MessageState messageState

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

    MessageState getMessageState() {
        return messageState
    }

    void setMessageState(MessageState messageState) {
        this.messageState = messageState
    }

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }
}
/**
 * 消息的状态
 */
enum MessageState {
    NO_READ,
    ALREADY_READ,
    DELETE
}