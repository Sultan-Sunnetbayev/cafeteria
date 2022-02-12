package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
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

    @PostMapping(path = "getProductByCode",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ResponseTransfer getProductByCode(@RequestBody ProductDTO productDTO){

        if(bucketService.AddProduct(productDTO.getCode())){

            return new ResponseTransfer("product successful found",true);
        }else{
            return new ResponseTransfer("product don't found",true);
        }

    }

//    @GetMapping(produces = "application/json")
//    public ResponseEntity get
//
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
//    public ResponseEntity SellProduct(@RequestParam("code")String code){
//
//        List<ProductDTO>productDTOS;
//        productDTOS=bucketService.getAllProduct();
//        Double allPrice;
//        for(ProductDTO productDTO:productDTOS){
//
//        }
//    }
}