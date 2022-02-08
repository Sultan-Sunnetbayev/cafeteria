package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/sellProduct")
public class SellProductController {

    private final ProductService productService;

    @Autowired
    public SellProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(produces = "application/json")
    public ProductDTO getProductByCode(String code){

        Product product=productService.getProductByCode(code);

        return ProductDTO.builder()
                .imagePath(product.getImagePath())
                .name(product.getName())
                .sellPrice(product.getSellPrice())

    }
}