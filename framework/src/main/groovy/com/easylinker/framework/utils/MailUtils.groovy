package com.easylinker.framework.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

import javax.mail.MessagingException

/**
 * @author wwhai* @date 2019/6/5 23:08
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class MailUtils {
    @Autowired
    private JavaMailSender javaMailSender

    @Value(value = '${spring.mail.username}')
    private String senderMailAddress

//    @Autowired
//    private TemplateEngine templateEngine

    void sendSimpleMail(Map<String, Object> valueMap) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage()
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true)
//        // 设置发件人邮箱
//        helper.setFrom(senderMailAddress)
//        // 设置收件人邮箱
//        helper.setTo((String[]) valueMap.get("to"))
//        // 设置邮件标题
//        helper.setSubject(valueMap.get("title").toString())
//
//        // 添加正文（使用thymeleaf模板）
//        Context context = new Context()
//        context.setVariables(valueMap)
//        String content = this.templateEngine.process("email_template", context)
//        helper.setText(content, true)
//
//        // 发送邮件
//        javaMailSender.send(mimeMessage)

    }

//    static void main(String[] args) {
//        Map<String, Object> valueMap = new HashMap<>();
//        valueMap.put("to", ["接收邮箱1", "接收邮箱2"]);
//        valueMap.put("title", "邮件标题");
//        valueMap.put("content", "邮件内容");
//
//        //sendSimpleMail(valueMap);
//
//    }
}
