package com.easylinker.v3.modules.message.dao


import com.easylinker.v3.modules.message.model.Message
import com.easylinker.v3.modules.message.model.MessageState
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by admin on 2019/6/11 10:20
 *
 */


interface MessageRepository extends JpaRepository<Message, Long> {
    Message findTopByUserSecurityId(String userSecurityId)

    Page<Message> findAllByUserSecurityIdAndMessageState(String userSecurityId, MessageState messageState, Pageable pageable)
}
