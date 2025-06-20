package co.gov.sic.testsic.service.interfaces;

import co.gov.sic.testsic.infrastructura.dto.request.ProcedureRequest;
import co.gov.sic.testsic.infrastructura.dto.response.ProcedureResponse;

import java.util.List;

public interface ProcedureServiceI {
    List<ProcedureResponse> findAll();
    ProcedureResponse findById(Long id);
    ProcedureResponse save(ProcedureRequest request);
    ProcedureResponse update(Long id, ProcedureRequest request);
    void delete(Long id);
}
