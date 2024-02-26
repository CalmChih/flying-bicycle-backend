package top.fallingintodreams.flying.bicycle.backend.common;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件发送工具
 *
 * @author Chih
 * @date 2024/2/26 0:56
 */
@Component
public class EmailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${flying.mail.from}")
    private String from;

    /**
     * 发送邮件
     * @param emailAccount 对方的邮箱
     * @param subject 邮件主题
     * @param text 邮件内容
     */
    public void sendEmail(String emailAccount, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(emailAccount);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

}
