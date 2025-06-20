package co.gov.sic.testsic.controller;

import co.gov.sic.testsic.infrastructura.dto.request.PersonRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DefaultResponse;
import co.gov.sic.testsic.infrastructura.dto.response.PersonResponse;
import co.gov.sic.testsic.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<DefaultResponse<PersonResponse>> createPerson(@Valid @RequestBody PersonRequest request) {
        try {
            PersonResponse person = personService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new DefaultResponse<>(false, "Person created successfully", HttpStatus.CREATED, person));
        } catch (Exception e) {
            logger.error("Error creating person", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error creating person", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<PersonResponse>> getById(@PathVariable Long id) {
        try {
            Optional<PersonResponse> person = personService.findById(id);
            if (person.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new DefaultResponse<>(true, "Person not found", HttpStatus.NOT_FOUND, null));
            }
            logger.info("Person found with id: {}", id);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Person found", HttpStatus.OK, person.get()));
        } catch (Exception e) {
            logger.error("Person not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DefaultResponse<>(true, "Person not found", HttpStatus.NOT_FOUND, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse<PersonResponse>> updatePerson(
            @PathVariable Long id, @Valid @RequestBody PersonRequest request) {
        try {
            PersonResponse updated = personService.update(id, request);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Person updated", HttpStatus.OK, updated));
        } catch (Exception e) {
            logger.error("Error updating person", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error updating person", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Void>> deletePerson(@PathVariable Long id) {
        try {
            personService.delete(id);
            return ResponseEntity.ok(new DefaultResponse<>(false, "Person deleted", HttpStatus.OK, null));
        } catch (Exception e) {
            logger.error("Error deleting person", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error deleting person", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<List<PersonResponse>>> listAll() {
        try {
            List<PersonResponse> people = personService.findAll();
            return ResponseEntity.ok(new DefaultResponse<>(false, "People list retrieved", HttpStatus.OK, people));
        } catch (Exception e) {
            logger.error("Error listing persons", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DefaultResponse<>(true, "Error listing persons", HttpStatus.INTERNAL_SERVER_ERROR, null));
        }
    }
}
