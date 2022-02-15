package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.BucketRepository;
import tm.salam.cafeteria3.dao.EmployeeRepository;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dao.SalesProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.SalesProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SalesProduct;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellProductServiceImpl implements SellProductService {

    private final SalesProductRepository salesProductRepository;
    private final EmployeeRepository employeeRepository;
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SellProductServiceImpl(SalesProductRepository salesProductRepository, EmployeeRepository employeeRepository, BucketRepository bucketRepository, ProductRepository productRepository) {
        this.salesProductRepository = salesProductRepository;
        this.employeeRepository = employeeRepository;
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<SalesProductDTO> getAllSalesProducts(){

        List<SalesProductDTO>salesProductDTOS=new ArrayList<>();
        List<SalesProduct>salesProducts=salesProductRepository.findAll();

        for(SalesProduct salesProduct:salesProducts){

            salesProductDTOS.add(
                    SalesProductDTO.builder()
                            .productName(salesProduct.getProductName())
                            .product(salesProduct.getProduct())
                            .amount(salesProduct.getAmount())
                            .imagePath(salesProduct.getImagePath())
                            .sellPrice(salesProduct.getSellPrice())
                            .employee(salesProduct.getEmployee())
                            .user(salesProduct.getUser())
                            .build()
            );
        }
        return salesProductDTOS;
    }

    @Override
    @Transactional
    public boolean SaveSalesProduct(){

        List<Bucket>buckets=bucketRepository.findAll();

        for (Bucket bucket:buckets){
            Product product=productRepository.getProductByCode(bucket.getProduct().getCode());

            if(product!=null){
                product.setAmount(product.getAmount()-bucket.getAmount());
            }
            SalesProduct salesProduct=SalesProduct.builder()
                            .product(bucket.getProduct())
                            .productName(bucket.getProduct().getName())
                            .amount(bucket.getAmount())
                            .imagePath(bucket.getProduct().getImagePath())
                            .employee(bucket.getEmployee())
                            .user(bucket.getUser())
                            .sellPrice(bucket.getProduct().getSellPrice())
                            .build();

            salesProductRepository.save(salesProduct);
        }

        return true;
    }

    @Override
    public List<ProductDTO> getBoughtProductsEmployee(int id) {

        List<SalesProduct> salesProducts = salesProductRepository.findByEmployeeId(id);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (SalesProduct salesProduct : salesProducts) {

            productDTOS.add(ProductDTO.builder()
                    .imagePath(salesProduct.getImagePath())
                    .name(salesProduct.getProductName())
                    .amount(salesProduct.getAmount())
                    .sellPrice(salesProduct.getSellPrice())
                    .build()

            );
        }
        return productDTOS;
    }
}