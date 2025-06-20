package co.gov.sic.testsic.infrastructura.repository;

import co.gov.sic.testsic.infrastructura.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
}
