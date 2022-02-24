package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.salam.cafeteria3.dto.ReturnProductDTO;
import tm.salam.cafeteria3.generator.QRCodeGenerator;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.service.BucketService;
import tm.salam.cafeteria3.service.EmployeeService;
import tm.salam.cafeteria3.service.ProductService;
import tm.salam.cafeteria3.service.ReturnProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/returnedProducts")
public class ReturnedProductController {

    private final ReturnProductService returnProductService;
    private final ProductService productService;
    private final BucketService bucketService;
    private final EmployeeService employeeService;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public ReturnedProductController(ReturnProductService returnProductService,
                                     ProductService productService,
                                     BucketService bucketService,
                                     EmployeeService employeeService,
                                     QRCodeGenerator qrCodeGenerator) {

        this.returnProductService = returnProductService;
        this.productService = productService;
        this.bucketService = bucketService;
        this.employeeService = employeeService;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @GetMapping(produces = "application/json")
    public List<ReturnProductDTO> getAllReturnedProducts() {

        List<ReturnProductDTO> returnProductDTOS = returnProductService.getAllReturnedProducts();

        return returnProductDTOS;
    }

    @PostMapping(path = "/addProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseEntity getProductByCode(@RequestParam("code") String code) {

        Map<Object, Object> response = new HashMap<>();
        if (!productService.findProductByCode(code)) {

            response.put("this product don't found in database", false);
            return ResponseEntity.ok(response);
        }
        boolean check = bucketService.AddProduct(code).getStatus().booleanValue();

        if (check) {

            response.put("product successfull added", true);
            response.put("returned products", bucketService.getAllProduct());

        } else {

            response.put("product don't added", false);
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping(path = "/getEmployee", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity getEmployeeByCode(@RequestParam("QRCode") MultipartFile multipartFile) throws IOException {

        String employeeCode = qrCodeGenerator.decodeQRCode(multipartFile);
        Employee employee = employeeService.getEmployeeByCode(employeeCode);
        Map<Object, Object> response = new HashMap<>();

        if (employee != null) {

            bucketService.setEmployeeInBucket(employee);
            response.put("employee successful found", employeeService.getEmployeeDTOByCode(employeeCode));

        } else {
            response.put("employee don't found", false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity ReturnProducts() {

        Map<Object, Object> response = new HashMap<>();
        List<Bucket> buckets = bucketService.getAllBucket();

        if (buckets == null || !bucketService.CheckClient()) {

            response.put("products don't returned", false);
        } else {
            if (returnProductService.SaveReturnProducts(buckets)) {

                response.put("all products successful returned", true);
            } else {

                response.put("products don't returned", false);
            }
        }
        bucketService.RemoveBucket();

        return ResponseEntity.ok(response);
    }

}