package tm.salam.cafeteria3.service;

import org.springframework.data.domain.Page;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProducts(Optional<String> productName);

    boolean AddOrEditProduct(ProductDTO productDTO);

    ProductDTO getProductDTOByCode(String code);

    boolean findProductByCode(String code);

    boolean RemoveProductByCode(String code);
}
