package co.gov.sic.testsic.controller;


import co.gov.sic.testsic.infrastructura.dto.request.DocumentTypeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DefaultResponse;
import co.gov.sic.testsic.infrastructura.dto.response.DocumentTypeResponse;
import co.gov.sic.testsic.service.interfaces.DocumentTypeServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeServiceI service;
    private final Logger logger = LoggerFactory.getLogger(DocumentTypeController.class);

    @GetMapping
    public ResponseEntity<DefaultResponse<List<DocumentTypeResponse>>> getAll() {
        try {
            List<DocumentTypeResponse> list = service.findAll();
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "List retrieved", HttpStatus.OK, list)
            );
        } catch (Exception e) {
            logger.error("Error getting all document types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<DocumentTypeResponse>> getById(@PathVariable Long id) {
        try {
            DocumentTypeResponse response = service.findById(id);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "DocumentType found", HttpStatus.OK, response)
            );
        } catch (Exception e) {
            logger.error("DocumentType not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<DocumentTypeResponse>> create(
            @Valid @RequestBody DocumentTypeRequest request) {
        try {
            DocumentTypeResponse saved = service.save(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new DefaultResponse<>(false, "DocumentType created", HttpStatus.CREATED, saved));
        } catch (Exception e) {
            logger.error("Error creating DocumentType", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error creating DocumentType", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse<DocumentTypeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DocumentTypeRequest request) {
        try {
            DocumentTypeResponse updated = service.update(id, request);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "DocumentType updated", HttpStatus.OK, updated));
        } catch (Exception e) {
            logger.error("Error updating DocumentType: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(
                    new DefaultResponse<>(false, "DocumentType deleted", HttpStatus.OK, null));
        } catch (Exception e) {
            logger.error("Error deleting DocumentType: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, e.getMessage(), HttpStatus.NOT_FOUND, null));
        }
    }
}
