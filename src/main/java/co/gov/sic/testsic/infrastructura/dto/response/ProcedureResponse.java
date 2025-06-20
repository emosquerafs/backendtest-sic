package co.gov.sic.testsic.infrastructura.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ProcedureResponse {
    private Long id;
    private String registrationNumber;
    private Integer registrationYear;
    private String name;
    private String description;
    private Long submittedById;
    private Long receivedById;
    private Instant createdAt;
    private Instant updatedAt;
}
