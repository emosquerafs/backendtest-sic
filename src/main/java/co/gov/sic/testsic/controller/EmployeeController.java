package co.gov.sic.testsic.controller;

import co.gov.sic.testsic.infrastructura.dto.request.EmployeeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DefaultResponse;
import co.gov.sic.testsic.infrastructura.dto.response.EmployeeResponse;
import co.gov.sic.testsic.service.interfaces.EmployeeServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceI service;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping
    public ResponseEntity<DefaultResponse<List<EmployeeResponse>>> getAll() {
        try {
            List<EmployeeResponse> employees = service.findAll();
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "Employees list retrieved", HttpStatus.OK, employees)
            );
        } catch (Exception e) {
            logger.error("Error listing employees", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error listing employees", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<EmployeeResponse>> getById(@PathVariable Long id) {
        try {
            EmployeeResponse employee = service.findById(id);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "Employee found", HttpStatus.OK, employee)
            );
        } catch (Exception e) {
            logger.error("Error finding employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<EmployeeResponse>> create(@Valid @RequestBody EmployeeRequest request) {
        try {
            EmployeeResponse created = service.save(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new DefaultResponse<>(false, "Employee created successfully", HttpStatus.CREATED, created));
        } catch (Exception e) {
            logger.error("Error creating employee", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse<EmployeeResponse>> update(
            @PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        try {
            EmployeeResponse updated = service.update(id, request);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "Employee updated", HttpStatus.OK, updated));
        } catch (Exception e) {
            logger.error("Error updating employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "Employee deleted", HttpStatus.OK, null));
        } catch (Exception e) {
            logger.error("Error deleting employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }
}