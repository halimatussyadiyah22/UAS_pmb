package org.halimatussyadiyah.pmb.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.halimatussyadiyah.pmb.enumeration.VerificationType;
import org.halimatussyadiyah.pmb.exception.ApiException;
import org.halimatussyadiyah.pmb.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String name, String email, String verificationUrl, VerificationType verificationType) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("halimahsyadiyah2202@gmail.com");
            message.setTo(email);
            message.setText(getEmailMessage(name, verificationUrl, verificationType));
            message.setSubject(String.format("User Management - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mailSender.send(message);
            log.info("Email sent to {}", name);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private String getEmailMessage(String name, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> { return "Hello " + name + "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl + "\n\nThe Support Team"; }
            case ACCOUNT -> { return "Hello " + name + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl + "\n\nThe Support Team"; }
            default -> throw new ApiException("Unable to send email. Email type unknown");
        }
    }
}
