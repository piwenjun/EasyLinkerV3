package com.easylinker.v3.modules.message.controller


import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.message.model.Message
import com.easylinker.v3.modules.message.model.MessageState
import com.easylinker.v3.modules.message.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 * 系统邮件
 * Created by admin on 2019/6/11 10:19
 *
 */

@RestController
@RequestMapping("/message")
class MessageController extends AbstractController {

    @Autowired
    MessageService messageService

    MessageController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }


    /**
     * 获取消息列表
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/list")
    R list(@RequestParam(required = true) int size,
           @RequestParam(required = true) int page,
           @RequestParam(required = true) MessageState messageState) {

        return R.okWithData(messageService.listByUser(getCurrentUser(), messageState,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))))
    }
    /**
     * 获取消息的状态
     * @return
     */
    @GetMapping("/listMessageState")

    R listMessageState() {
        return R.okWithData(MessageState.values())
    }

    /**
     * 标记阅读
     * @return
     */
    @PutMapping("/markRead")
    R markRead(@RequestParam(required = true) String securityId) {

        Message message = messageService.getByUserSecurityId(securityId)
        if (message) {
            messageService.markRead(message)
            return R.ok()
        } else {
            return R.error("消息不存在")
        }
    }
}
