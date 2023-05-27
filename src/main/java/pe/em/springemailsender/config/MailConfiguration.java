package pe.em.springemailsender.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    @Value("${email.sender.default}")
    private String emailUser;

    @Value("${email.sender.password}")
    private String emailPassword;

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailUser);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        // indicando el protocolo que vamos usar
        props.put("mail.transport.protocol", "smtp");
        // obliga a realizar autenticacion previa al envio de correo


        props.put("mail.smtp.auth", "true");
        // cifra toda comunicacion, envio de correo cifrado
        props.put("mail.smtp.starttls.enable", "true");
        // muestra informacion en la consolo de los pasos de envio de correo, usado para
        // dev no para prod
        props.put("mail.debug", "true");

        return mailSender;
    }

}
