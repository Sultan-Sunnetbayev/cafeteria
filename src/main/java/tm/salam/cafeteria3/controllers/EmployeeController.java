package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    public List<EmployeeDTO> ShowAllEmployee(){

        return employeeService.getAllEmployee();
    }

    @PostMapping(path = "/register",consumes = "application/json",produces = "application/json")
    public List<EmployeeDTO> CreateNewEmployee(@RequestBody EmployeeDTO employeeDTO){

        employeeService.CreateNewEmployee(employeeDTO);

        return employeeService.getAllEmployee();
    }

    @PutMapping(consumes = "application/json",produces = "application/json")
    public List<EmployeeDTO> UpdateProfile(@RequestBody EmployeeDTO employeeDTO){

        employeeService.UpdateEmployeeProfile(employeeDTO);

        return employeeService.getAllEmployee();
    }

    @DeleteMapping(path = "/{id}/id",produces = "application/json")
    public List<EmployeeDTO>RemoveEmployee(@PathVariable("id")int id){

        employeeService.RemoveEmployee(id);

        return employeeService.getAllEmployee();
    }
}