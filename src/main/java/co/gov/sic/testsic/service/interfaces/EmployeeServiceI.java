package co.gov.sic.testsic.service.interfaces;

import co.gov.sic.testsic.infrastructura.dto.request.EmployeeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeServiceI {
    List<EmployeeResponse> findAll();
    EmployeeResponse findById(Long id);
    EmployeeResponse save(EmployeeRequest request);
    EmployeeResponse update(Long id, EmployeeRequest request);
    void delete(Long id);
}
