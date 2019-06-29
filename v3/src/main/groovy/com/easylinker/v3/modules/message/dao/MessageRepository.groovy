package com.easylinker.v3.modules.message.dao


import com.easylinker.v3.modules.message.model.Message
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by admin on 2019/6/11 10:20
 *
 */


interface MessageRepository extends JpaRepository<Message, Long> {
}
