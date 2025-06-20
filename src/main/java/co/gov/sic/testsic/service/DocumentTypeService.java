package co.gov.sic.testsic.service;

import co.gov.sic.testsic.infrastructura.dto.request.DocumentTypeRequest;
import co.gov.sic.testsic.infrastructura.dto.response.DocumentTypeResponse;
import co.gov.sic.testsic.infrastructura.entity.DocumentType;
import co.gov.sic.testsic.infrastructura.repository.DocumentTypeRepository;
import co.gov.sic.testsic.service.interfaces.DocumentTypeServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentTypeService implements DocumentTypeServiceI {

    private final DocumentTypeRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<DocumentTypeResponse> findAll() {
        return repository.findAll().stream()
                .map(documentType -> objectMapper.convertValue(documentType, DocumentTypeResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentTypeResponse findById(Long id) {
        return repository.findById(id)
                .map(documentType -> objectMapper.convertValue(documentType, DocumentTypeResponse.class))
                .orElseThrow(() -> new EntityNotFoundException("DocumentType not found with id: " + id));
    }

    @Override
    public DocumentTypeResponse save(DocumentTypeRequest request) {
        DocumentType entity = objectMapper.convertValue(request, DocumentType.class);
        DocumentType saved = repository.save(entity);
        return objectMapper.convertValue(saved, DocumentTypeResponse.class);
    }

    @Override
    public DocumentTypeResponse update(Long id, DocumentTypeRequest request) {
        DocumentType existingDocumentType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DocumentType not found with id: " + id));

        DocumentType updatedDocumentType = objectMapper.convertValue(request, DocumentType.class);
        DocumentType saved = repository.save(updatedDocumentType);
        return objectMapper.convertValue(saved, DocumentTypeResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("DocumentType not found with id: " + id);
        }
        repository.deleteById(id);
    }
}