package co.gov.sic.testsic.service.interfaces;

import co.gov.sic.testsic.infrastructura.dto.request.PersonRequest;
import co.gov.sic.testsic.infrastructura.dto.response.PersonResponse;
import co.gov.sic.testsic.infrastructura.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonServiceI {
    List<PersonResponse> findAll();
    Optional<PersonResponse> findById(Long id);
    PersonResponse save(PersonRequest personRequest);
    PersonResponse update(Long id, PersonRequest personRequest);
    void delete(Long id);
}