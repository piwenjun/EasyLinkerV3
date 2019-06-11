package com.easylinker.framework.modules.message.dao


import com.easylinker.framework.modules.message.model.Message
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by admin on 2019/6/11 10:20
 *
 */


interface MessageRepository extends JpaRepository<Message,Long> {
}
