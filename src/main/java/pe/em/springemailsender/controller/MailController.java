package pe.em.springemailsender.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.em.springemailsender.domain.EmailDto;
import pe.em.springemailsender.domain.EmailFileDto;
import pe.em.springemailsender.service.IEmailService;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class MailController {

    private final IEmailService emailService;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> recieveRequestEmail(@RequestBody EmailDto emailDto) {

        log.info("[Message recieved] " + emailDto);

        emailService.sendEmail(emailDto.getToUser(), emailDto.getSubject(), emailDto.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sendMessageFile")
    public ResponseEntity<?> recieveRequestEmailWithFile(@ModelAttribute EmailFileDto emailFileDto) {

        Map<String, String> response = new HashMap<>();

        try {

            String fileName = emailFileDto.getFile().getOriginalFilename();
            Path path = Paths.get("src/main/resources/files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDto.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();

            emailService.sendEmailWithFile(emailFileDto.getToUser(), emailFileDto.getSubject(),
                    emailFileDto.getMessage(), file);

            response.put("estado", "Enviado");
            response.put("archivo", fileName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el email con el archivo " +
                    e.getMessage());
        }
    }

}
