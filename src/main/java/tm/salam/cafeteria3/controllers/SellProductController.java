package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tm.salam.cafeteria3.service.ProductService;

@RestController
@RequestMapping("/api/sellProduct")
public class SellProductController {

    private final ProductService productService;

    @Autowired
    public SellProductController(ProductService productService) {
        this.productService = productService;
    }


}
