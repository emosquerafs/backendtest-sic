package co.gov.sic.testsic.service.interfaces;

import co.gov.sic.testsic.infrastructura.dto.request.DocumentTypeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DocumentTypeResponse;

import java.util.List;

public interface DocumentTypeServiceI {
    List<DocumentTypeResponse> findAll();
    DocumentTypeResponse findById(Long id);
    DocumentTypeResponse save(DocumentTypeRequest request);
    DocumentTypeResponse update(Long id, DocumentTypeRequest request);
    void delete(Long id);
}
