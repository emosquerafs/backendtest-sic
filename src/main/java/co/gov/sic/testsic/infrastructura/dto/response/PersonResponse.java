package co.gov.sic.testsic.infrastructura.dto.response;

import co.gov.sic.testsic.infrastructura.entity.DocumentType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {

    private Long id;
    private String idNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private DocumentTypeResponse documentType;
}
