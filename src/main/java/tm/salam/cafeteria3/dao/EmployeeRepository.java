package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findFirstByName(String username);
    Employee findById(int id);
}
