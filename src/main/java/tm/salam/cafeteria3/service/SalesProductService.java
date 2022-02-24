package tm.salam.cafeteria3.service;

import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.SalesProductDTO;
import tm.salam.cafeteria3.models.SalesProduct;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalesProductService {

//    List<SalesProductDTO>getAllSalesProducts();

    boolean SaveSalesProducts();

    List<ProductDTO> getSalesProductsWithDate(LocalDate date);

    List<ProductDTO> getAllSalesProducts();

    long getAmountSalesProducts();

    double getPriceSalesProducts();
}
