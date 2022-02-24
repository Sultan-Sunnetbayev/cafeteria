package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dao.ReturnProductRepository;
import tm.salam.cafeteria3.dao.SalesProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.ReturnProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.ReturnProduct;
import tm.salam.cafeteria3.models.SalesProduct;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnProductServiceImpl implements ReturnProductService {

    private final ReturnProductRepository returnProductRepository;
    private final ProductRepository productRepository;
    private final SalesProductRepository salesProductRepository;

    @Autowired
    public ReturnProductServiceImpl(ReturnProductRepository returnProductRepository,
                                    ProductRepository productRepository,
                                    SalesProductRepository salesProductRepository) {
        this.returnProductRepository = returnProductRepository;
        this.productRepository = productRepository;
        this.salesProductRepository = salesProductRepository;
    }

    @Override
    public List<ReturnProductDTO> getAllReturnedProducts() {

        List<ReturnProduct> returnProducts = returnProductRepository.findAll();
        List<ReturnProductDTO> returnProductDTOS = new ArrayList<>();

        for (ReturnProduct returnProduct : returnProducts) {
            returnProductDTOS.add(
                    ReturnProductDTO.builder()
                            .productName(returnProduct.getProductName())
                            .imagePath(returnProduct.getImagePath())
                            .sellPrice(returnProduct.getSellPrice())
                            .amount(returnProduct.getAmount())
                            .employeeName(returnProduct.getEmployee().getName())
                            .build()
            );
        }

        return returnProductDTOS;
    }

    @Override
    @Transactional
    public boolean SaveReturnProducts(List<Bucket> buckets) {

        for (Bucket bucket : buckets) {

            Product product = productRepository.getProductByCode(bucket.getProduct().getCode());
            List<SalesProduct> salesProducts = salesProductRepository.
                    findByEmployee_IdAndProduct_IdAndAmountGreaterThan(bucket.getEmployee().getId(), bucket.getProduct().getId(), 0);
            int productAmount = bucket.getAmount();

            if (salesProducts != null) {
                for (SalesProduct salesProduct : salesProducts) {

                    if (productAmount > 0 && salesProduct.getAmount() <= productAmount) {
                        productAmount -= salesProduct.getAmount();
                        product.setAmount(product.getAmount() + salesProduct.getAmount());
                        salesProduct.setAmount(0);
                    } else if (productAmount > 0) {
                        salesProduct.setAmount(salesProduct.getAmount() - productAmount);
                        product.setAmount(product.getAmount() + productAmount);
                    }
                }

                ReturnProduct returnProduct = ReturnProduct.builder()
                        .product(bucket.getProduct())
                        .imagePath(bucket.getProduct().getImagePath())
                        .productName(bucket.getProduct().getName())
                        .amount(bucket.getAmount() - productAmount)
                        .sellPrice(bucket.getProduct().getSellPrice())
                        .employee(bucket.getEmployee())
                        .build();

                returnProductRepository.save(returnProduct);
            }
        }


        return true;
    }

}