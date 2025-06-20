package co.gov.sic.testsic.infrastructura.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResponse {
    private Long id;
    private PersonResponse person;
    private String department;
    private LocalDate hireDate;
    private Instant createdAt;
    private Instant updatedAt;
}
