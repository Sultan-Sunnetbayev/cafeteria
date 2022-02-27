package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.Helper.ResponseTransfer;
import tm.salam.cafeteria3.dao.BucketRepository;
import tm.salam.cafeteria3.dao.EmployeeRepository;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.Product;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;

    @Autowired
    public BucketServiceImpl(ProductRepository productRepository, BucketRepository bucketRepository) {
        this.productRepository = productRepository;
        this.bucketRepository = bucketRepository;
    }

    @Override
    @Transactional
    public ResponseTransfer AddProduct(String code) {

        Product product = productRepository.findByCode(code);

        if (product == null) {
            return new ResponseTransfer("sales product don't found in database", false);
        }

        Bucket bucket = bucketRepository.findBucketByProduct_Id(product.getId());

        if (bucket == null) {
            bucket = new Bucket();
            bucket.setProduct(product);
            bucket.setSum(product.getSellPrice());
            bucket.setAmount(1);
        } else {
            bucket.setAmount(bucket.getAmount() + 1);
            bucket.setSum(bucket.getSum() + product.getSellPrice());
        }
        bucketRepository.save(bucket);

        Bucket bucket1 = bucketRepository.findById(bucket.getId());
        ResponseTransfer response = new ResponseTransfer();

        if (bucket1 == null) {
            response.setMessage("sell products don't added");
            response.setStatus(false);
        } else {
            response.setMessage("success");
            response.setStatus(true);
        }

        return response;
    }

    @Override
    public List<ProductDTO> getAllProduct() {

        List<Bucket> buckets = bucketRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Bucket bucket : buckets) {
            productDTOList.add(ProductDTO.builder()
                    .id(bucket.getId())
                    .imagePath(bucket.getProduct().getImagePath())
                    .name(bucket.getProduct().getName())
                    .amount(bucket.getAmount())
                    .sellPrice(bucket.getProduct().getSellPrice())
                    .sum(bucket.getSum())
                    .code(bucket.getProduct().getCode())
                    .build());
        }

        return productDTOList;
    }

    @Override
    @Transactional
    public boolean RemoveBucket() {

        bucketRepository.deleteAll();

        return true;
    }

    @Override
    @Transactional
    public void setEmployeeInBucket(Employee employee) {

        List<Bucket> buckets = bucketRepository.findAll();

        for (Bucket bucket : buckets) {
            bucket.setEmployee(employee);
        }

    }

    @Override
    public List<Bucket> getAllBucket() {

        return bucketRepository.findAll();

    }

    @Override
    public boolean CheckClient() {

        List<Bucket> buckets = bucketRepository.findAll();

        for (Bucket bucket : buckets) {

            if (bucket.getEmployee() == null) {
                return false;
            }
        }

        return true;
    }
}