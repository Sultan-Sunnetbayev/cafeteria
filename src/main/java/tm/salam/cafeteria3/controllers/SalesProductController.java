package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.service.SalesProductService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/salesProducts")
public class SalesProductController {

    private final SalesProductService salesProductService;


    @Autowired
    public SalesProductController(SalesProductService salesProductService) {
        this.salesProductService = salesProductService;
    }


    @GetMapping(produces = "application/json")
    public List<ProductDTO>ShowAllSalesProducts(){

        return salesProductService.getAllSalesProducts();
    }


    @PostMapping(path = "/date",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public List<ProductDTO>ShowAllSalesProductsByDate(@RequestParam("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        return salesProductService.getSalesProductsWithDate(date);
    }
}
