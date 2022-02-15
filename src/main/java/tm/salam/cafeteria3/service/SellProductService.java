package tm.salam.cafeteria3.service;


import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.SalesProductDTO;

import java.util.List;

public interface SellProductService {

    boolean SaveSalesProduct();
    List<SalesProductDTO>getAllSalesProducts();
    List<ProductDTO>getBoughtProductsEmployee(int id);
}
