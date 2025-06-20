package co.gov.sic.testsic.service;

import co.gov.sic.testsic.infrastructura.dto.request.PersonRequest;
import co.gov.sic.testsic.infrastructura.dto.response.PersonResponse;
import co.gov.sic.testsic.infrastructura.entity.Person;
import co.gov.sic.testsic.infrastructura.repository.PersonRepository;
import co.gov.sic.testsic.service.interfaces.PersonServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService implements PersonServiceI {

    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream()
                .map(person -> objectMapper.convertValue(person, PersonResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonResponse> findById(Long id) {
        return personRepository.findById(id)
                .map(person -> objectMapper.convertValue(person, PersonResponse.class));
    }

    @Override
    public PersonResponse save(PersonRequest personRequest) {
        Person person = objectMapper.convertValue(personRequest, Person.class);
        Person saved = personRepository.save(person);
        return objectMapper.convertValue(saved, PersonResponse.class);
    }

    @Override
    public PersonResponse update(Long id, PersonRequest personRequest) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

        Person updatedPerson = objectMapper.convertValue(personRequest, Person.class);

        existingPerson.setIdNumber(updatedPerson.getIdNumber());
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setPhone(updatedPerson.getPhone());
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setDocumentType(updatedPerson.getDocumentType());
        existingPerson.setUpdatedAt(updatedPerson.getUpdatedAt());

        Person saved = personRepository.save(existingPerson);
        return objectMapper.convertValue(saved, PersonResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new EntityNotFoundException("Person not found with id: " + id);
        }
        personRepository.deleteById(id);
    }
}
