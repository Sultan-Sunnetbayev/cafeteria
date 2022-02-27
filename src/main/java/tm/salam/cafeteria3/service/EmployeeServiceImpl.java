package tm.salam.cafeteria3.service;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dao.EmployeeRepository;
import tm.salam.cafeteria3.dao.ReturnProductRepository;
import tm.salam.cafeteria3.dao.SalesProductRepository;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.generator.QRCodeGenerator;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.ReturnProduct;
import tm.salam.cafeteria3.models.SalesProduct;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final SalesProductRepository salesProductRepository;
    private final ReturnProductRepository returnProductRepository;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               PasswordEncoder passwordEncoder,
                               SalesProductRepository salesProductRepository,
                               ReturnProductRepository returnProductRepository,
                               QRCodeGenerator qrCodeGenerator) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.salesProductRepository = salesProductRepository;
        this.returnProductRepository = returnProductRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {

        return employeeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO toDTO(Employee employee) {

        return EmployeeDTO.builder()
                .id(employee.getId())
                .imagePath(employee.getImagePath())
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .build();
    }

    @Override
    @Transactional
    public ResponseTransfer CreateNewEmployee(EmployeeDTO employeeDTO) {

        ResponseTransfer response = new ResponseTransfer();
        Employee helper = employeeRepository.findEmployeeByNameAndSurname(employeeDTO.getName(), employeeDTO.getSurname());
        if (helper != null) {

            response.setMessage("employee with name and surname already added");
            response.setStatus(false);

            return response;
        }
        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .password(passwordEncoder.encode(employeeDTO.getPassword()))
                .grade(employeeDTO.getGrade())
                .imagePath(employeeDTO.getImagePath())
                .build();

        employeeRepository.save(employee);

        if (employeeRepository.findEmployeeByNameAndSurname(employeeDTO.getName(), employeeDTO.getSurname()) != null) {

            try {
                employee.setCode(qrCodeGenerator.GenerateQRCode(String.valueOf(String.valueOf(employee.getId()))));
                response.setMessage("success");
                response.setStatus(true);

            } catch (IOException e) {
                e.printStackTrace();
                response.setMessage("employee don't added");
                response.setStatus(false);

            } catch (WriterException e) {
                e.printStackTrace();
                response.setMessage("employee don't added");
                response.setStatus(false);

            }

        } else {

            response.setMessage("employee don't added");
            response.setStatus(false);
        }

        return response;
    }

    @Override
    @Transactional
    public ResponseTransfer UpdateEmployeeProfile(EmployeeDTO employeeDTO) {

        Employee savedEmployee = employeeRepository.findEmployeeById(employeeDTO.getId());

        if (savedEmployee == null) {

            return new ResponseTransfer("employee don't found", false);
        }
        if (!Objects.equals(employeeDTO.getName(), savedEmployee.getName()) &&
                !Objects.equals(employeeDTO.getSurname(), savedEmployee.getSurname())) {

            if (employeeRepository.findEmployeeByNameAndSurname(employeeDTO.getName(), employeeDTO.getSurname()) != null) {

                return new ResponseTransfer("employee with name and surname already added", false);
            }
        }

        savedEmployee.setName(employeeDTO.getName());
        savedEmployee.setSurname(employeeDTO.getSurname());
        if (!passwordEncoder.matches(employeeDTO.getPassword(), savedEmployee.getPassword())) {
            savedEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        }
        if (!Objects.equals(employeeDTO.getGrade(), savedEmployee.getGrade())) {
            savedEmployee.setGrade(employeeDTO.getGrade());
        }
        savedEmployee.setImagePath(employeeDTO.getImagePath());

        employeeRepository.save(savedEmployee);

        return new ResponseTransfer("succes", true);
    }

    @Override
    @Transactional
    public boolean RemoveEmployee(int id) {

        Employee employee = employeeRepository.findEmployeeById(id);

        if (employee == null) {

            return false;
        }
        employeeRepository.deleteById(id);

        return true;
    }

    @Override
    public EmployeeDTO getEmployeeDTOById(int id) {

        Employee employee = employeeRepository.findEmployeeById(id);

        if (employee == null) {

            return null;
        }

        return EmployeeDTO.builder()
                .id(employee.getId())
                .imagePath(employee.getImagePath())
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .build();
    }

    @Override
    public Employee getEmployeeByCode(String code) {

        code = code + ".png";
        Employee employee = employeeRepository.findByCode(code);

        if (employee == null) {

            return null;
        } else {

            return employee;
        }

    }

    @Override
    public EmployeeDTO getEmployeeDTOByCode(String code) {

        code = code + ".png";
        Employee employee = employeeRepository.findByCode(code);

        if (employee == null) {

            return null;

        } else {

            return EmployeeDTO.builder()
                    .id(employee.getId())
                    .imagePath(employee.getImagePath())
                    .name(employee.getName())
                    .surname(employee.getSurname())
                    .grade(employee.getGrade())
                    .build();
        }
    }

    @Override
    public List<ProductDTO> getAllBoughtProducts(int id) {

        List<SalesProduct> salesProducts = salesProductRepository.findByEmployeeId(id);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (SalesProduct salesProduct : salesProducts) {
            productDTOS.add(
                    ProductDTO.builder()
                            .id(salesProduct.getId())
                            .imagePath(salesProduct.getImagePath())
                            .name(salesProduct.getProductName())
                            .sellPrice(salesProduct.getSellPrice())
                            .amount(salesProduct.getAmount())
                            .build()
            );
        }

        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllReturnedProducts(int id) {

        List<ReturnProduct> returnProducts = returnProductRepository.findByEmployeeId(id);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (ReturnProduct returnProduct : returnProducts) {

            productDTOS.add(
                    ProductDTO.builder()
                            .id(returnProduct.getId())
                            .imagePath(returnProduct.getImagePath())
                            .name(returnProduct.getProductName())
                            .amount(returnProduct.getAmount())
                            .sellPrice(returnProduct.getSellPrice())
                            .build()
            );
        }

        return productDTOS;
    }
}