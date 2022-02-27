package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.BucketRepository;
import tm.salam.cafeteria3.dao.EmployeeRepository;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dto.EmployeeDTO;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Employee;

import java.util.List;

@Service
public class SellProductServiceImpl implements SellProductService {

    private final SalesProductService salesProductService;
    private final EmployeeRepository employeeRepository;
    private final BucketService bucketService;
    private final ProductRepository productRepository;

    @Autowired
    public SellProductServiceImpl(SalesProductService salesProductService,
                                  EmployeeRepository employeeRepository,
                                  BucketService bucketService, ProductRepository productRepository) {
        this.salesProductService = salesProductService;
        this.employeeRepository = employeeRepository;
        this.bucketService = bucketService;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public boolean AddSellProductToBucket(String code) {

        if (productRepository.findByCode(code) != null) {

            bucketService.AddProduct(code);

            return true;
        } else {

            return false;
        }

    }

    @Override
    public List<ProductDTO> getAllSellProducts() {

        return bucketService.getAllProduct();
    }

    @Override
    public EmployeeDTO getClientByCode(String code) {

        code = code + ".png";
        Employee employee = employeeRepository.findByCode(code);

        if (employee == null) {

            return null;
        }
        bucketService.setEmployeeInBucket(employee);

        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .imagePath(employee.getImagePath())
                .grade(employee.getGrade())
                .build();

    }

    @Override
    @Transactional
    public boolean SaveSellProducts() {

        if (bucketService.CheckClient()) {

            salesProductService.SaveSalesProducts();
            bucketService.RemoveBucket();

            return true;
        } else {

            return false;
        }
    }
}