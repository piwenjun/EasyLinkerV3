package com.easylinker.v3.modules.message.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.message.model.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Created by admin on 2019/6/11 10:20
 *
 */

@Service
class MessageService extends AbstractService<Message> {
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
    void deleteById(long id) {

    }
}
