package pe.em.springemailsender.service;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    @Value("${email.sender.default}")
    private String emailUser;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String[] toUser, String subject, String message) {
        // Metodo para enviar correo
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailUser);
        // aqui podemos enviar a un correo o una lista de correos, String, String ...
        mailMessage.setTo(toUser);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

    }

    @Override
    public void sendEmailWithFile(String[] toUser, String subject, String message, File file) {
        // Metodo para enviar correo con un archivo
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // Usamos el standar UTF-8, el mas usado pero tener en consideracion de acuerdo
            // al negocio
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, 1, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(emailUser);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            // Seteamos el nombre del archivo y luego el file
            mimeMessageHelper.addAttachment(file.getName(), file);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
