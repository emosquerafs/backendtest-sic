package co.gov.sic.testsic.infrastructura.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureResponse {
    private Long id;
    private String registrationNumber;
    private Integer registrationYear;
    private String name;
    private String description;
    private PersonResponse submittedBy;
    private EmployeeResponse receivedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
