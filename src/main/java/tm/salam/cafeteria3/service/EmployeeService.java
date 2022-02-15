package tm.salam.cafeteria3.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.models.Employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO>getAllEmployee();
    boolean CreateNewEmployee(EmployeeDTO employeeDTO);
    boolean UpdateEmployeeProfile(EmployeeDTO employeeDTO);
    boolean RemoveEmployee(int id);
    EmployeeDTO getEmployeeById(int id);
    Employee getEmployeeByCode(String code);
    EmployeeDTO getEmployeeDTOByCode(String code);
}
