package co.gov.sic.testsic.infrastructura.dto.response;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class EmployeeResponse {
    private Long id;
    private Long personId;
    private String department;
    private LocalDate hireDate;
    private Instant createdAt;
    private Instant updatedAt;
}
