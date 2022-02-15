package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.service.BucketService;
import tm.salam.cafeteria3.service.EmployeeService;
import tm.salam.cafeteria3.service.SellProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sellProduct")
public class SellProductController {

    private final BucketService bucketService;
    private final EmployeeService employeeService;
    private final SellProductService sellProductService;

    @Autowired
    public SellProductController(BucketService bucketService,
                                 EmployeeService employeeService,
                                 SellProductService sellProductService) {
        this.bucketService = bucketService;
        this.employeeService = employeeService;
        this.sellProductService = sellProductService;
    }

    @PostMapping(path = "/getProductByCode",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity getProductByCode(@RequestParam("code")String code){

        Map<Object,Object>response=new HashMap<>();

        if(bucketService.AddProduct(code)){

            response.put("all product ",bucketService.getAllProduct());

        }else{
                response.put("error with products",false);
            }
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity ShowSumSalesProducts(){

        List<ProductDTO>productDTOS=bucketService.getAllProduct();
        Double sum =0.0;

        for(ProductDTO productDTO:productDTOS){
            sum+=productDTO.getSum();
        }

        return ResponseEntity.ok(sum);
    }

    @PostMapping(path = "/getEmployeeByCode",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public EmployeeDTO getEmployeeByCode(@RequestParam("code")String code){


        Employee employee=employeeService.getEmployeeByCode(code);
        bucketService.setEmployeeInBucket(employee);

        return EmployeeDTO.builder()
                .imagePath(employee.getImagePath())
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .build();

    }

    @PostMapping(produces = "application/json")
    public ResponseEntity SellProduct(){

        Map<Object,Object>response=new HashMap<>();

        if(sellProductService.SaveSalesProduct()){

            response.put("all sales products succesfull saved",true);
        }else{
            response.put("products don't saved",false);
        }

        bucketService.RemoveBucket();
        return ResponseEntity.ok(response);
    }

}