package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.EmployeeRepository;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private  final EmployeeRepository employeeRepository;
    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {

        return employeeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO toDTO(Employee employee) {

        return EmployeeDTO.builder()
                .imagePath(employee.getImagePath())
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .build();
    }

    @Override
    @Transactional
    public boolean CreateNewEmployee(EmployeeDTO employeeDTO) {

        Employee helper1=employeeRepository.findFirstByName(employeeDTO.getName());
        Employee helper2=employeeRepository.findFirstBySurname(employeeDTO.getSurname());
        if(helper1!=null && helper2!=null){
            return false;
        }
        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .password(passwordEncoder.encode(employeeDTO.getPassword()))
                .grade(employeeDTO.getGrade())
                .imagePath(employeeDTO.getImagePath())
                .role(Role.CLIENT)
                .build();

        employeeRepository.save(employee);

        return true;
    }

    @Override
    @Transactional
    public boolean UpdateEmployeeProfile(EmployeeDTO employeeDTO) {


            Employee savedEmployee=employeeRepository.findById(employeeDTO.getId());

            if(savedEmployee==null){
                return false;
            }
            savedEmployee.setName(employeeDTO.getName());
            savedEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            savedEmployee.setSurname(employeeDTO.getSurname());
            savedEmployee.setGrade(employeeDTO.getGrade());
            savedEmployee.setImagePath(employeeDTO.getImagePath());
            employeeRepository.save(savedEmployee);

            return true;
        }

    @Override
    public boolean RemoveEmployee(int id) {

        Employee employee=employeeRepository.findById(id);
        if(employee==null){
            return false;
        }
        employeeRepository.deleteById(id);

        return true;
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {

        Employee employee=employeeRepository.findById(id);

        return EmployeeDTO.builder()
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .build();
    }



}