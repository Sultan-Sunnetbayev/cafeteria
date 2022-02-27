package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.BucketRepository;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dao.SalesProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.SalesProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SalesProduct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesProductServiceImpl implements SalesProductService {

    private final SalesProductRepository salesProductRepository;
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SalesProductServiceImpl(SalesProductRepository salesProductRepository,
                                   BucketRepository bucketRepository,
                                   ProductRepository productRepository) {
        this.salesProductRepository = salesProductRepository;
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllSalesProducts() {

        List<ProductDTO> productDTOS = new ArrayList<>();
        List<SalesProduct> salesProducts = salesProductRepository.findAll();

        for (SalesProduct salesProduct : salesProducts) {

            productDTOS.add(
                    ProductDTO.builder()
                            .id(salesProduct.getId())
                            .name(salesProduct.getProductName())
                            .imagePath(salesProduct.getImagePath())
                            .amount(salesProduct.getAmount())
                            .imagePath(salesProduct.getImagePath())
                            .sellPrice(salesProduct.getSellPrice())
                            .build()
            );
        }

        return productDTOS;
    }

    @Override
    @Transactional
    public boolean SaveSalesProducts() {

        List<Bucket> buckets = bucketRepository.findAll();

        for (Bucket bucket : buckets) {
            Product product = productRepository.getProductByCode(bucket.getProduct().getCode());
            int productAmount = bucket.getAmount();

            if (product != null) {
                if (productAmount <= product.getAmount()) {
                    product.setAmount(product.getAmount() - productAmount);
                    productAmount = 0;
                } else {
                    productAmount -= product.getAmount();
                    product.setAmount(0);
                }
                SalesProduct salesProduct = SalesProduct.builder()
                        .product(bucket.getProduct())
                        .productName(bucket.getProduct().getName())
                        .amount(bucket.getAmount() - productAmount)
                        .imagePath(bucket.getProduct().getImagePath())
                        .employee(bucket.getEmployee())
                        .sellPrice(bucket.getProduct().getSellPrice())
                        .build();

                salesProductRepository.save(salesProduct);
            }
        }

        return true;
    }

    @Override
    public List<ProductDTO> getSalesProductsWithDate(LocalDate date) {

        List<SalesProduct> salesProducts = salesProductRepository.findByCreated(date);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (SalesProduct salesProduct : salesProducts) {

            productDTOS.add(ProductDTO.builder()
                    .id(salesProduct.getId())
                    .name(salesProduct.getProductName())
                    .imagePath(salesProduct.getImagePath())
                    .amount(salesProduct.getAmount())
                    .sellPrice(salesProduct.getSellPrice())
                    .build());
        }

        return productDTOS;
    }


    @Override
    public long getAmountSalesProducts() {

        long amount = 0;
        List<ProductDTO> productDTOS = this.getAllSalesProducts();

        for (ProductDTO productDTO : productDTOS) {
            amount += productDTO.getAmount();
        }

        return amount;
    }

    @Override
    public double getPriceSalesProducts() {

        double sum = 0.0;
        List<ProductDTO> productDTOS = this.getAllSalesProducts();

        for (ProductDTO productDTO : productDTOS) {
            sum += productDTO.getAmount() * productDTO.getSellPrice();
        }

        return sum;
    }
}