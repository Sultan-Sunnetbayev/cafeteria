package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findFirstByName(String name);
    Employee findFirstBySurname(String surname);
    Employee findById(int id);
    Employee findByCode(String code);
}
