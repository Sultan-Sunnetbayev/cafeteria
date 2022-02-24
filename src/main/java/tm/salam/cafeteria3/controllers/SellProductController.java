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
import tm.salam.cafeteria3.service.SalesProductService;
import tm.salam.cafeteria3.service.SellProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sellProduct")
public class SellProductController {

    private final SellProductService sellProductService;

    @Autowired
    public SellProductController(SellProductService sellProductService) {
        this.sellProductService = sellProductService;
    }

    @PostMapping(path = "/getProductByCode",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity getProductByCode(@RequestParam("code") String code) {

        Map<Object, Object> response = new HashMap<>();

        if (sellProductService.AddSellProductToBucket(code)) {

            response.put("all product ", sellProductService.getAllSellProducts());

        } else {
            response.put("this products don't found in database or amount product less than amount product in database", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity ShowSumSalesProducts() {

        List<ProductDTO> productDTOS = sellProductService.getAllSellProducts();
        Double sum = 0.0;

        for (ProductDTO productDTO : productDTOS) {
            sum += productDTO.getSum();
        }

        return ResponseEntity.ok(sum);
    }

    @PostMapping(path = "/getEmployeeByCode", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseEntity getEmployeeByCode(@RequestParam("code") String code) {

        EmployeeDTO employeeDTO = sellProductService.getClientByCode(code);
        Map<Object, Object> response = new HashMap<>();

        if (employeeDTO == null) {

            response.put("this employee don't found in database", false);
        } else {

            response.put("this employee successful founded", true);
            response.put("employee ", employeeDTO);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity SellProduct() {

        Map<Object, Object> response = new HashMap<>();

        if (sellProductService.SaveSellProducts()) {

            response.put("all sales products succesfull saved", true);
        } else {

            response.put("products don't saved", false);
        }

        return ResponseEntity.ok(response);
    }

}