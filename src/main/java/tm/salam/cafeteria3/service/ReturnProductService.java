package tm.salam.cafeteria3.service;

import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.ReturnProductDTO;
import tm.salam.cafeteria3.models.Bucket;

import java.util.List;

public interface ReturnProductService {

    boolean SaveReturnProducts(List<Bucket> buckets);

    List<ReturnProductDTO> getAllReturnedProducts();

}
