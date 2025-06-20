package co.gov.sic.testsic.controller;

import co.gov.sic.testsic.infrastructura.dto.request.ProcedureRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DefaultResponse;
import co.gov.sic.testsic.infrastructura.dto.response.ProcedureResponse;
import co.gov.sic.testsic.service.interfaces.ProcedureServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedures")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureServiceI service;
    private static final Logger logger = LoggerFactory.getLogger(ProcedureController.class);

    @GetMapping
    public ResponseEntity<DefaultResponse<List<ProcedureResponse>>> getAll() {
        try {
            List<ProcedureResponse> list = service.findAll();
            return ResponseEntity.ok(new DefaultResponse<>(false, "List retrieved", HttpStatus.OK, list));
        } catch (Exception e) {
            logger.error("Error retrieving procedures", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error retrieving procedures", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<ProcedureResponse>> getById(@PathVariable Long id) {
        try {
            ProcedureResponse response = service.findById(id);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Procedure found", HttpStatus.OK, response));
        } catch (Exception e) {
            logger.error("Error finding procedure", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<ProcedureResponse>> create(@Valid @RequestBody ProcedureRequest request) {
        try {
            ProcedureResponse created = service.save(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new DefaultResponse<>(false, "Procedure created", HttpStatus.CREATED, created));
        } catch (Exception e) {
            logger.error("Error creating procedure", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error creating procedure", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse<ProcedureResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProcedureRequest request) {
        try {
            ProcedureResponse updated = service.update(id, request);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Procedure updated", HttpStatus.OK, updated));
        } catch (Exception e) {
            logger.error("Error updating procedure", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Procedure deleted", HttpStatus.OK, null));
        } catch (Exception e) {
            logger.error("Error deleting procedure", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }
}
