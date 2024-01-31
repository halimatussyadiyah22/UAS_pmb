package org.halimatussyadiyah.pmb.service;


import org.halimatussyadiyah.pmb.enumeration.VerificationType;

public interface EmailService {
    void sendVerificationEmail(String name,
                               String email,
                               String verificationUrl,
                               VerificationType verificationType);
}
