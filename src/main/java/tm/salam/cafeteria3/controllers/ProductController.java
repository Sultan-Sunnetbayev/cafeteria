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
    public ProductDTO getProductByCode(@ModelAttribute String code){

        Product product=null;
        System.out.println("code  product  "+code);
        if(productService.findProductByCode(code)){

            product=productService.getProductByCode(code);

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
    @PutMapping(path = "/updateProduct",consumes = "application/json",produces = "application/json")
    public List<ProductDTO>UpdateProduct(@RequestBody ProductDTO productDTO){

        productService.AddOrEditProduct(productDTO);

        return productService.getAllProducts();
    }

}