package tm.salam.cafeteria3.service;

import org.springframework.http.ResponseEntity;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.Product;

import java.util.List;

public interface BucketService {

    ResponseTransfer AddProduct(String code);

    List<ProductDTO> getAllProduct();

    boolean RemoveBucket();

    void setEmployeeInBucket(Employee employee);

    List<Bucket> getAllBucket();

    boolean CheckClient();
}
