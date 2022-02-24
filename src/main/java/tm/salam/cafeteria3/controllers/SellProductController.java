package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.generator.QRCodeGenerator;
import tm.salam.cafeteria3.service.SellProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sellProduct")
public class SellProductController {

    private final SellProductService sellProductService;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public SellProductController(SellProductService sellProductService,
                                 QRCodeGenerator qrCodeGenerator) {
        this.sellProductService = sellProductService;
        this.qrCodeGenerator = qrCodeGenerator;
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
    public ResponseEntity getEmployeeByCode(@RequestParam("QRCode") MultipartFile multipartFile) throws IOException {

        EmployeeDTO employeeDTO = sellProductService.getClientByCode(qrCodeGenerator.decodeQRCode(multipartFile));
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