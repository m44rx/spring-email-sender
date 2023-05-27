package pe.em.springemailsender.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String[] toUser;
    private String subject;
    private String message;
}
