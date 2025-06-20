package co.gov.sic.testsic.infrastructura.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcedureRequest {
    @NotBlank
    private String registrationNumber;

    @NotNull
    private Integer registrationYear;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long submittedById;

    @NotNull
    private Long receivedById;
}
