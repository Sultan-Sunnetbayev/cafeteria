package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.BucketService;
import tm.salam.cafeteria3.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellProduct")
public class SellProductController {

    private final ProductService productService;
    private final BucketService bucketService;
    @Autowired
    public SellProductController(ProductService productService, BucketService bucketService) {
        this.productService = productService;
        this.bucketService = bucketService;
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    public List<ProductDTO> getProductByCode(@RequestBody ProductDTO productDTO){

        bucketService.AddProducts(productDTO.getCode());

        return bucketService.getAllProduct();
    }
}