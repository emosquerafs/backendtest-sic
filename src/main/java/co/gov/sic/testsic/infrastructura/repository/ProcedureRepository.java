package co.gov.sic.testsic.infrastructura.repository;

import co.gov.sic.testsic.infrastructura.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
}
