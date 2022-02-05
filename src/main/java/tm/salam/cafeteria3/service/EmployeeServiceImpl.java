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
    public void CreateNewEmployee(EmployeeDTO employeeDTO) {

        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .password(passwordEncoder.encode(employeeDTO.getPassword()))
                .email(employeeDTO.getEmail())
                .grade(employeeDTO.getGrade())
                .role(Role.CLIENT)
                .build();

        employeeRepository.save(employee);

    }

    @Override
    @Transactional
    public void UpdateEmployeeProfile(EmployeeDTO employeeDTO) {


            Employee savedEmployee=employeeRepository.findById(employeeDTO.getId());

            if(savedEmployee==null){
                throw new RuntimeException("User not found by name "+employeeDTO.getName());
            }
            savedEmployee.setName(employeeDTO.getName());
            savedEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            savedEmployee.setSurname(employeeDTO.getSurname());
            savedEmployee.setGrade(employeeDTO.getGrade());
            savedEmployee.setImagePath(employeeDTO.getImagePath());
            employeeRepository.save(savedEmployee);
        }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findFirstByName(username);

        if(employee == null){
            throw new UsernameNotFoundException("Employeer not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(employee.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                employee.getName(),
                employee.getPassword(),
                roles);

    }

    @Override
    public void RemoveEmployee(int id) {

        employeeRepository.deleteById(id);
    }

}