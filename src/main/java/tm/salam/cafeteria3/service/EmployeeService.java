package tm.salam.cafeteria3.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.models.Employee;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    List<EmployeeDTO>getAllEmployee();
    void CreateNewEmployee(EmployeeDTO employeeDTO);
    void UpdateEmployeeProfile(EmployeeDTO employeeDTO);
    void RemoveEmployee(int id);
}
