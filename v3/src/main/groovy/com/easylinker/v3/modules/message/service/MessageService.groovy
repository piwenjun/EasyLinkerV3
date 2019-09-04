package com.easylinker.v3.modules.message.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.message.dao.MessageRepository
import com.easylinker.v3.modules.message.model.Message
import com.easylinker.v3.modules.message.model.MessageState
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Created by admin on 2019/6/11 10:20
 *
 */

@Service
class MessageService extends AbstractService<Message> {
    @Autowired
    MessageRepository messageRepository

    @Override
    void save(Message message) {

    }

    @Override
    Page<Message> page(Pageable pageable) {
        return null
    }

    @Override
    Message getById(long id) {
        return null
    }

    @Override
    void delete(Message message) {

    }

    @Override
    void deleteById(long id) {

    }

    /**
     * 根据用户查找
     * @param appUser
     * @param pageable
     * @return
     */
    Page<Message> listByUser(AppUser appUser, MessageState messageState, Pageable pageable) {

        return messageRepository.findAllByUserSecurityIdAndMessageState(appUser.securityId, messageState, pageable)
    }
    /**
     * 标记阅读
     * @param message
     */
    void markRead(Message message) {
        message.setMessageState(MessageState.ALREADY_READ)
        messageRepository.save(message)
    }
    /**
     * 查找一个Message
     * @param securityId
     * @return
     */
    Message getBySecurityId(String securityId){
        return messageRepository.findTopBySecurityId(securityId)
    }

}
