package tm.salam.cafeteria3.service;

import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.SpoiledProduct;

import java.util.List;

public interface SpoiledProductService {

    boolean AddProducts(List<ProductDTO>productDTOS);
    boolean findProductById(int id);
    SpoiledProduct getSpoiledProductById(int id);
    List<ProductDTO>getAddedProducts();
}
