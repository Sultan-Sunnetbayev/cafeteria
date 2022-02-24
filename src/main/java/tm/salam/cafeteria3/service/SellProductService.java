package tm.salam.cafeteria3.service;


import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import java.util.List;

public interface SellProductService {

    boolean AddSellProductToBucket(String code);
    List<ProductDTO>getAllSellProducts();
    EmployeeDTO getClientByCode(String code);
    boolean SaveSellProducts();
}
