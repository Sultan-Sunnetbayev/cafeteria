package tm.salam.cafeteria3.service;

import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;

import java.util.List;

public interface ProductService {

    List<ProductDTO>getAllProducts();
    boolean AddOrEditProduct(ProductDTO productDTO);
    ProductDTO getProductByCode(String code);
    boolean findProductByCode(String code);
    boolean RemoveProduct(String code);
}
