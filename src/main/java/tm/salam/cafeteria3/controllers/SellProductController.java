package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public ProductDTO getProductByCode(@RequestBody ProductDTO productDTO){

        Product product=productService.getProductByCode(productDTO.getCode());

        return ProductDTO.builder()
                .imagePath(product.getImagePath())
                .name(product.getName())
                .sellPrice(product.getSellPrice())
                .amount(product.getAmount())
                .build();

    }
}