package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tm.salam.cafeteria3.service.SalesProductService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/main")
public class MainController {

    private final SalesProductService salesProductService;

    @Autowired
    public MainController(SalesProductService salesProductService) {
        this.salesProductService = salesProductService;
    }

    @GetMapping()
    public ResponseEntity Main(){

        Map<Object,Object>response=new HashMap<>();

        response.put("amount sales products",salesProductService.getAmountSalesProducts());
        response.put("cost all sales products",salesProductService.getPriceSalesProducts());

        return ResponseEntity.ok(response);
    }
}