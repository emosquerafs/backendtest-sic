package co.gov.sic.testsic.infrastructura.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeRequest {
    private Long id;
    private String code;
    private String description;
}
