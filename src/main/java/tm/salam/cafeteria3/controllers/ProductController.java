package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
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


    @PostMapping(path = "/getProductByCode",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE },produces = "application/json")
    public ProductDTO getProductByCode(@RequestParam String code){

        if(productService.findProductByCode(code)){

            ProductDTO productDTO=productService.getProductByCode(code);

            return productDTO;

        }else{

            ProductDTO temporal=new ProductDTO();
            temporal.setCode(code);
            return temporal;
        }

    }
    @PutMapping(path = "/addOrEditProduct",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE },produces = "application/json")
    @ResponseBody
    public ResponseTransfer AddOrEditProduct(@ModelAttribute ProductDTO productDTO){

        if(productService.AddOrEditProduct(productDTO)){
            return new ResponseTransfer("product successful edited",true);
        }else{
            return new ResponseTransfer("product don't edited",false);
        }

    }

    @DeleteMapping(path = "/removeProduct",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    @ResponseBody
    public ResponseTransfer RemoveProduct(@RequestParam String code){

        if (productService.RemoveProduct(code)){
            return new ResponseTransfer("product successful removed",true);
        }else{
            return new ResponseTransfer("product don't removed",false);
        }
    }
}