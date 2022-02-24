package tm.salam.cafeteria3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.salam.cafeteria3.Helper.FileUploadUtil;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public List<ProductDTO> ShowAllProducts(@RequestParam(value = "name", required = false) Optional<String> name) {

        return productService.getAllProducts(name);
    }


    @GetMapping(path = "/getProductByCode", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    public ProductDTO getProductByCode(@RequestParam("code") String code) {

        ProductDTO productDTO;

        if (productService.findProductByCode(code)) {

            productDTO = productService.getProductDTOByCode(code);

        } else {

            productDTO = new ProductDTO();
            productDTO.setCode(code);
        }

        return productDTO;
    }


    @PutMapping(path = "/addOrEditProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = "application/json")
    @ResponseBody
    public ResponseTransfer AddOrEditProduct(@ModelAttribute ProductDTO productDTO,
                                             @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        productDTO.setImagePath(fileName);

        String uploadDir = "src/main/resources/product_photos";

        if (productService.AddOrEditProduct(productDTO)) {

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return new ResponseTransfer("product successful added or edited", true);
        } else {

            return new ResponseTransfer("product don't added or edited", false);
        }

    }


    @DeleteMapping(path = "/removeProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    @ResponseBody
    public ResponseTransfer RemoveProduct(@RequestParam("code") String code) {

        if (productService.RemoveProductByCode(code)) {

            return new ResponseTransfer("product successful removed", true);
        } else {

            return new ResponseTransfer("product don't removed", false);
        }
    }
}