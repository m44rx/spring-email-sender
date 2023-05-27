package pe.em.springemailsender.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailFileDto {

    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;
    
}
