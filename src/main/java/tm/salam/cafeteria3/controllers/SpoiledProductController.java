package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.service.BucketService;
import tm.salam.cafeteria3.service.ProductService;
import tm.salam.cafeteria3.service.SpoiledProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/spoiledProducts")
public class SpoiledProductController {

    private final ProductService productService;
    private final SpoiledProductService spoiledProductService;
    private final BucketService bucketService;

    @Autowired
    public SpoiledProductController(ProductService productService,
                                    SpoiledProductService spoiledProductService,
                                    BucketService bucketService) {

        this.productService = productService;
        this.spoiledProductService = spoiledProductService;
        this.bucketService = bucketService;
    }


    @GetMapping(produces = "application/json")
    public List<ProductDTO> ShowAllSpoiledProduct() {

        return spoiledProductService.getAddedProducts();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    @ResponseBody
    public ResponseTransfer AddSpoiledProduct(@RequestParam("code") String code) {

        ResponseTransfer responseTransfer;

        if (productService.findProductByCode(code)) {

            responseTransfer = bucketService.AddProduct(code);
            boolean check = responseTransfer.getStatus().booleanValue();

            if (check) {

                return responseTransfer;
            } else {

                return responseTransfer;
            }

        } else {

            return new ResponseTransfer("product don't added", false);
        }
    }

    @PostMapping(path = "/addSpoiledProducts", produces = "application/json")
    public ResponseTransfer SaveSpoiledProducts() {

        List<ProductDTO> allProduct = bucketService.getAllProduct();

        if (spoiledProductService.AddProducts(allProduct)) {

            bucketService.RemoveBucket();

            return new ResponseTransfer("all spoiled product successful added", true);

        } else {

            bucketService.RemoveBucket();

            return new ResponseTransfer("products don't added", false);
        }

    }
}