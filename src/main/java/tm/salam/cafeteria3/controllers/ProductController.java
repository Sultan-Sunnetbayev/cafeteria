package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<ProductDTO> ShowAllProducts(){

        return productService.getAllProducts();
    }

    @PostMapping(path = "/addProduct",consumes = "application/json",produces = "application/json")
    public List<ProductDTO>AddNewProduct(@RequestBody ProductDTO productDTO){

        productService.AddOrEditProduct(productDTO);

        return productService.getAllProducts();
    }

    @PutMapping(consumes = "application/json",produces = "application/json")
    public List<ProductDTO>EditProduct(@RequestBody ProductDTO productDTO){

        productService.AddOrEditProduct(productDTO);

        return productService.getAllProducts();
    }

}