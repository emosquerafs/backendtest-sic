package co.gov.sic.testsic.service;

import co.gov.sic.testsic.infrastructura.dto.request.ProcedureRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DocumentTypeResponse;
import co.gov.sic.testsic.infrastructura.dto.response.EmployeeResponse;
import co.gov.sic.testsic.infrastructura.dto.response.PersonResponse;
import co.gov.sic.testsic.infrastructura.dto.response.ProcedureResponse;
import co.gov.sic.testsic.infrastructura.entity.DocumentType;
import co.gov.sic.testsic.infrastructura.entity.Employee;
import co.gov.sic.testsic.infrastructura.entity.Person;
import co.gov.sic.testsic.infrastructura.entity.Procedure;
import co.gov.sic.testsic.infrastructura.repository.EmployeeRepository;
import co.gov.sic.testsic.infrastructura.repository.PersonRepository;
import co.gov.sic.testsic.infrastructura.repository.ProcedureRepository;
import co.gov.sic.testsic.service.interfaces.ProcedureServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcedureService implements ProcedureServiceI {

    private final ProcedureRepository repository;
    private final PersonRepository personRepository;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<ProcedureResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProcedureResponse findById(Long id) {
        return mapToResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Procedure not found with id: " + id)));
    }

    @Override
    public ProcedureResponse save(ProcedureRequest request) {
        Procedure procedure = new Procedure();
        procedure.setRegistrationNumber(request.getRegistrationNumber());
        procedure.setRegistrationYear(request.getRegistrationYear());
        procedure.setName(request.getName());
        procedure.setDescription(request.getDescription());

        Person person = personRepository.findById(request.getSubmittedById())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        Employee employee = employeeRepository.findById(request.getReceivedById())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        procedure.setSubmittedBy(person);
        procedure.setReceivedBy(employee);

        Procedure saved = repository.save(procedure);

        // Construcción manual del response
        ProcedureResponse response = new ProcedureResponse();
        response.setId(saved.getId());
        response.setRegistrationNumber(saved.getRegistrationNumber());
        response.setRegistrationYear(saved.getRegistrationYear());
        response.setName(saved.getName());
        response.setDescription(saved.getDescription());


        PersonResponse personResponse = objectMapper.convertValue(person, PersonResponse.class);
        DocumentType documentType = person.getDocumentType();
        personResponse.setDocumentType(objectMapper.convertValue(documentType, DocumentTypeResponse.class));
        response.setSubmittedBy(personResponse);


        EmployeeResponse employeeResponse = objectMapper.convertValue(employee, EmployeeResponse.class);
        Person employeePerson = employee.getPerson();
        PersonResponse employeePersonResponse = objectMapper.convertValue(employeePerson, PersonResponse.class);
        employeePersonResponse.setDocumentType(objectMapper.convertValue(employeePerson.getDocumentType(), DocumentTypeResponse.class));
        employeeResponse.setPerson(employeePersonResponse);

        response.setReceivedBy(employeeResponse);

        return response;
    }



    @Override
    public ProcedureResponse update(Long id, ProcedureRequest request) {
        Procedure procedure = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Procedure not found"));

        procedure.setRegistrationNumber(request.getRegistrationNumber());
        procedure.setRegistrationYear(request.getRegistrationYear());
        procedure.setName(request.getName());
        procedure.setDescription(request.getDescription());
        procedure.setSubmittedBy(personRepository.findById(request.getSubmittedById())
                .orElseThrow(() -> new EntityNotFoundException("Person not found")));
        procedure.setReceivedBy(employeeRepository.findById(request.getReceivedById())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found")));

        return mapToResponse(repository.save(procedure));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Procedure not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private ProcedureResponse mapToResponse(Procedure entity) {
        return ProcedureResponse.builder()
                .id(entity.getId())
                .registrationNumber(entity.getRegistrationNumber())
                .registrationYear(entity.getRegistrationYear())
                .name(entity.getName())
                .description(entity.getDescription())
                .submittedBy(objectMapper.convertValue(entity.getSubmittedBy(), PersonResponse.class))
                .receivedBy(objectMapper.convertValue(entity.getReceivedBy(), EmployeeResponse.class))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
