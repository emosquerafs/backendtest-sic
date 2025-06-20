package co.gov.sic.testsic.infrastructura.repository;

import co.gov.sic.testsic.infrastructura.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
