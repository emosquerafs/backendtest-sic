package co.gov.sic.testsic.infrastructura.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeResponse {
    private Long id;
    private String code;
    private String description;
}
