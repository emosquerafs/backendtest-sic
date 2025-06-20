package co.gov.sic.testsic.infrastructura.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {
    @NotNull
    private Long personId;

    @NotBlank
    private String department;

    @NotNull
    private LocalDate hireDate;
}
