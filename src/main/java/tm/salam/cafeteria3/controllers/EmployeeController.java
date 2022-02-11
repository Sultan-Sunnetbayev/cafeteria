package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.salam.cafeteria3.Helper.FileUploadUtil;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.service.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
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

    @PostMapping(path = "/addEmployee",consumes ={ MediaType.MULTIPART_FORM_DATA_VALUE },produces = "application/json")
    @ResponseBody
    public ResponseTransfer CreateNewEmployee(@ModelAttribute EmployeeDTO employeeDTO,
                                              @RequestParam("image")MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        employeeDTO.setImagePath(fileName);

        String uploadDir = "src/main/resources/employee_photos";

        if (employeeService.CreateNewEmployee(employeeDTO)){
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return new ResponseTransfer("employee successful added",true);
        }else{
            return new ResponseTransfer("employee don't added",false);
        }

    }

    @PostMapping(path="/getEmployeeById",consumes ={ MediaType.MULTIPART_FORM_DATA_VALUE },produces = "application/json")
    public EmployeeDTO getEmployeeById(@RequestParam int id){

        return employeeService.getEmployeeById(id);
    }

    @PutMapping(path = "/updateProfile",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },produces = "application/json")
    @ResponseBody
    public ResponseTransfer UpdateProfileEmployee(@RequestBody EmployeeDTO employeeDTO){

        if(employeeService.UpdateEmployeeProfile(employeeDTO)) {
            return new ResponseTransfer("employee successful updated", true);
        }else{
            return new ResponseTransfer("employee don't updated",false);
        }
    }

    @DeleteMapping(path = "/removeEmployee",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    @ResponseBody
    public ResponseTransfer RemoveEmployee(@RequestParam("id")int id){

        if(employeeService.RemoveEmployee(id)) {
            return new ResponseTransfer("employee successful removed", true);
        }else {
            return new ResponseTransfer("employee don't removed", false);
        }
    }
}