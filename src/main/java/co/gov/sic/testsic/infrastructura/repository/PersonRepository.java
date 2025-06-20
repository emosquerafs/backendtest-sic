package co.gov.sic.testsic.infrastructura.repository;

import co.gov.sic.testsic.infrastructura.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByIdNumber(String idNumber);
}
