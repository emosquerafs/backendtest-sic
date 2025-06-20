package co.gov.sic.testsic.service;

import co.gov.sic.testsic.infrastructura.dto.request.EmployeeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.EmployeeResponse;
import co.gov.sic.testsic.infrastructura.dto.response.PersonResponse;
import co.gov.sic.testsic.infrastructura.entity.Employee;
import co.gov.sic.testsic.infrastructura.entity.Person;
import co.gov.sic.testsic.infrastructura.repository.EmployeeRepository;
import co.gov.sic.testsic.infrastructura.repository.PersonRepository;
import co.gov.sic.testsic.service.interfaces.EmployeeServiceI;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class EmployeeService implements EmployeeServiceI {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper;

    public EmployeeService(EmployeeRepository employeeRepository, PersonRepository personRepository, ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.personRepository = personRepository;
        this.objectMapper = objectMapper;
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream()
                .map(employee -> EmployeeResponse.builder()
                        .id(employee.getId())
                        .department(employee.getDepartment())
                        .hireDate(employee.getHireDate())
                        .person(objectMapper.convertValue(employee.getPerson(), PersonResponse.class))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        return EmployeeResponse.builder()
                .id(employee.getId())
                .department(employee.getDepartment())
                .hireDate(employee.getHireDate())
                .person(objectMapper.convertValue(employee.getPerson(), PersonResponse.class))
                .build();
    }

    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + request.getPersonId()));

        Employee employee = Employee.builder()
                .department(request.getDepartment())
                .hireDate(request.getHireDate())
                .person(person)
                .build();

        Employee saved = employeeRepository.save(employee);
        return EmployeeResponse.builder()
                .id(saved.getId())
                .department(saved.getDepartment())
                .hireDate(saved.getHireDate())
                .person(objectMapper.convertValue(saved.getPerson(), PersonResponse.class))
                .build();
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + request.getPersonId()));

        existing.setDepartment(request.getDepartment());
        existing.setHireDate(request.getHireDate());
        existing.setPerson(person);

        Employee updated = employeeRepository.save(existing);
        return EmployeeResponse.builder()
                .id(updated.getId())
                .department(updated.getDepartment())
                .hireDate(updated.getHireDate())
                .person(objectMapper.convertValue(updated.getPerson(), PersonResponse.class))
                .build();
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}