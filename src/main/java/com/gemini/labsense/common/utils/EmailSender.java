package com.gemini.labsense.common.utils;

import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.ResultCodeEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;


/**
 * 邮件发送器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * 邮件实体
     */
    @Builder
    @Getter
    @Setter
    public static class Email {
        private String from;
        private String fromName;
        private String to;
        private String subject;
        private List<String> cc;
        private List<String> bcc;
        private String template;
        private Context context;//数据信息
    }


    /**
     * 获取基础邮件
     *
     * @param email 邮件
     * @return MimeMessage
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    private MimeMessage getMimeMessage(Email email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        // 设置发送方
        if (email.getFromName() != null) {
            helper.setFrom(email.getFrom(), email.getFromName());
        } else {
            helper.setFrom(email.getFrom());
        }
        // 设置接收方
        helper.setTo(email.getTo());
        // 设置邮件主题
        helper.setSubject(email.getSubject());
        List<String> bcc = email.getBcc();
        List<String> cc = email.getCc();
        if (cc != null && !cc.isEmpty()) {
            helper.setCc(cc.toArray(String[]::new));
        }
        if (bcc != null && !bcc.isEmpty()) {
            helper.setBcc(bcc.toArray(String[]::new));
        }
        return msg;
    }

    /**
     * 发送带模板的邮件
     *
     * @param templateEmail 模版邮件
     * @param files         附带文件
     */
    public void sendEmailWithTemplate(Email templateEmail, List<MultipartFile> files) {
        try {
            MimeMessage mimeMessage = getMimeMessage(templateEmail);
            //设置邮件信息
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //合并模版与数据并设置内容
            mimeMessageHelper.setText(this.templateEngine.process(templateEmail.getTemplate(), templateEmail.getContext()), true);
            // 添加附件
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }
            //发送
            this.mailSender.send(mimeMessage);
            //日志
            log.info("邮件发送成功：{}->{}", templateEmail.getFrom(), templateEmail.getTo());
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new LabSenseException(ResultCodeEnum.EMAIL_ERROR);
        }
    }

}

