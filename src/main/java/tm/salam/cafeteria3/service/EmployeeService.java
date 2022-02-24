package tm.salam.cafeteria3.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployee();

    ResponseTransfer CreateNewEmployee(EmployeeDTO employeeDTO);

    ResponseTransfer UpdateEmployeeProfile(EmployeeDTO employeeDTO);

    boolean RemoveEmployee(int id);

    EmployeeDTO getEmployeeDTOById(int id);

    Employee getEmployeeByCode(String code);

    EmployeeDTO getEmployeeDTOByCode(String code);

    List<ProductDTO> getAllBoughtProducts(int id);

    List<ProductDTO> getAllReturnedProducts(int id);
}
