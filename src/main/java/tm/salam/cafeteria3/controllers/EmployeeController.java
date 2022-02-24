package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.salam.cafeteria3.Helper.FileUploadUtil;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.service.EmployeeService;
import tm.salam.cafeteria3.service.ReturnProductService;
import tm.salam.cafeteria3.service.SalesProductService;
import tm.salam.cafeteria3.service.SellProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(produces = "application/json")
    public List<EmployeeDTO> ShowAllEmployee() {

        return employeeService.getAllEmployee();
    }


    @PostMapping(path = "/addEmployee", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseTransfer CreateNewEmployee(@ModelAttribute EmployeeDTO employeeDTO,
                                              @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        employeeDTO.setImagePath(fileName);

        String uploadDir = "src/main/resources/employee_photos";
        ResponseTransfer responseTransfer = employeeService.CreateNewEmployee(employeeDTO);
        boolean check = responseTransfer.getStatus().booleanValue();

        if (check) {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return responseTransfer;
        } else {

            return responseTransfer;
        }

    }


    @GetMapping(path = "/getEmployeeById", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity getEmployeeById(@RequestParam("id") int id) {

        EmployeeDTO employeeDTO = employeeService.getEmployeeDTOById(id);
        Map<Object, Object> response = new HashMap<>();

        if (employeeDTO == null) {

            response.put("employee don't found", false);
        } else {

            response.put("employee successful founded", true);
            response.put("employee", employeeDTO);
        }

        return ResponseEntity.ok(response);
    }


    @PutMapping(path = "/updateProfile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseTransfer UpdateProfileEmployee(@ModelAttribute EmployeeDTO employeeDTO) {

        ResponseTransfer responseTransfer = employeeService.UpdateEmployeeProfile(employeeDTO);
        boolean check = responseTransfer.getStatus().booleanValue();
        if (check) {

            return responseTransfer;
        } else {

            return responseTransfer;
        }
    }


    @DeleteMapping(path = "/removeEmployee", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseTransfer RemoveEmployee(@RequestParam("id") int id) {

        if (employeeService.RemoveEmployee(id)) {
            return new ResponseTransfer("employee successful removed", true);
        } else {
            return new ResponseTransfer("employee don't removed", false);
        }
    }


    @GetMapping(path = "/getBoughtProducts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public List<ProductDTO> getBoughtProducts(@RequestParam("id") int id) {

        return employeeService.getAllBoughtProducts(id);
    }


    @GetMapping(path = "/getReturnedProducts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public List<ProductDTO> getReturnedProducts(@RequestParam("id") int id) {

        return employeeService.getAllReturnedProducts(id);
    }

}