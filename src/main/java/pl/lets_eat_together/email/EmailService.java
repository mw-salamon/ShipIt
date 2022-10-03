package pl.lets_eat_together.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Override
    public void send(String sendTo, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            messageHelper.setTo(sendTo);
            messageHelper.setFrom("hello@letseattogether.pl");
            messageHelper.setSubject("Confirm your email and LET'S EAT TOGETHER :)");
            messageHelper.setText(email, true);
            mailSender.send(mimeMessage);

        } catch (MessagingException e){
            logger.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
