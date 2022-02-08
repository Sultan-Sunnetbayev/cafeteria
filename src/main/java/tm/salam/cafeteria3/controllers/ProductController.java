package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.ProductDTO.ProductDTOBuilder;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products")
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


    @PostMapping(path = "/addOrEditProduct",consumes = "application/json",produces = "application/json")
    public ProductDTO getProductByCode(@RequestBody ProductDTO productDTO){

        Product product=null;
        if(productService.findProductByCode(productDTO.getCode())){

            product=productService.getProductByCode(productDTO.getCode());

            return ProductDTO.builder()
                    .imagePath(product.getImagePath())
                    .name(product.getName())
                    .amount(product.getAmount())
                    .takenPrice(product.getTakenPrice())
                    .sellPrice(product.getSellPrice())
                    .code(product.getCode())
                    .build();
        }else{
                return ProductDTO.builder()
                        .code(product.getCode())
                        .build();
        }
    }
    @PutMapping(path = "/addOrEditProduct",consumes = "application/json",produces = "application/json")
    public List<ProductDTO>UpdateProduct(@RequestBody ProductDTO productDTO){

        productService.AddOrEditProduct(productDTO);

        return productService.getAllProducts();
    }

}