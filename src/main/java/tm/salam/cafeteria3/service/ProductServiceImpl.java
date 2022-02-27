package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.service.specification.ProductSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSpecification productSpecification;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductSpecification productSpecification) {
        this.productRepository = productRepository;
        this.productSpecification = productSpecification;
    }

    @Override
    public List<ProductDTO> getAllProducts(Optional<String> parameter) {

        ProductSpecification productSpecification = new ProductSpecification();

        List<Product> products1 = productRepository.
                findAll(productSpecification.ProductFilterByParameter(parameter.get()));
        List<Product> products2 = null;
        List<Product> products3 = null;
        if (CheckParameterIsIntegerNumber(parameter.get())) {
            products2 = productRepository.findAll(productSpecification.ProductFilterByAmount(parameter.get()));
        }
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products1) {
            productDTOS.add(toDTO(product));
        }
        if (products2 != null) {
            for (Product product : products2) {
                productDTOS.add(toDTO(product));
            }
        }
        if (CheckParameterIsDoubleNumber(parameter.get())) {
            products3 = productRepository.findAll(productSpecification.ProductFilterByPrice(parameter.get()));
        }
        if (products3 != null) {
            for (Product product : products3) {
                productDTOS.add(toDTO(product));
            }
        }

        return productDTOS;
    }

    private boolean CheckParameterIsIntegerNumber(String str) {

        if (str.isEmpty() || str == null) {
            return false;
        }
        boolean check = true;

        for (int i = 0; i < str.length() && check; i++) {
            if (!(Character.isDigit(str.toCharArray()[i]))) {
                check = false;
            }
        }

        return check;
    }

    private boolean CheckParameterIsDoubleNumber(String str) {

        if (str.isEmpty() || str == null) {
            return false;
        }
        boolean check = true;
        int scr = 0;
        for (int i = 0; i < str.length() && check; i++) {

            if (str.toCharArray()[i] == '.') {
                scr++;
            }
            if (!(Character.isDigit(str.toCharArray()[i]) || Objects.equals(str.toCharArray()[i], '.'))) {
                check = false;
            }
        }
        if (scr > 1) check = false;

        return check;
    }


    private ProductDTO toDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .imagePath(product.getImagePath())
                .name(product.getName())
                .amount(product.getAmount())
                .takenPrice(product.getTakenPrice())
                .sellPrice(product.getSellPrice())
                .code(product.getCode())
                .build();
    }

    @Override
    @Transactional
    public boolean AddOrEditProduct(ProductDTO productDTO) {

        Product product = productRepository.getProductByCode(productDTO.getCode());

        if (product != null) {

            product.setImagePath(productDTO.getImagePath());
            product.setName(productDTO.getName());
            product.setTakenPrice(productDTO.getTakenPrice());
            product.setSellPrice(productDTO.getSellPrice());
            product.setAmount(productDTO.getAmount());
            product.setCode(productDTO.getCode());

        } else {
            product = Product.builder()
                    .imagePath(productDTO.getImagePath())
                    .name(productDTO.getName())
                    .amount(productDTO.getAmount())
                    .takenPrice(productDTO.getTakenPrice())
                    .sellPrice(productDTO.getSellPrice())
                    .code(productDTO.getCode())
                    .build();
        }
        productRepository.save(product);

        if (productRepository.findByName(product.getName()) == null) {

            return false;
        } else {

            return true;
        }
    }

    @Override
    public boolean findProductByCode(String code) {

        Product product = productRepository.getProductByCode(code);

        if (product == null) {

            return false;
        } else {

            return true;
        }
    }

    @Override
    public ProductDTO getProductDTOByCode(String code) {

        Product product = productRepository.getProductByCode(code);

        return ProductDTO.builder()
                .id(product.getId())
                .imagePath(product.getImagePath())
                .name(product.getName())
                .amount(product.getAmount())
                .takenPrice(product.getTakenPrice())
                .sellPrice(product.getSellPrice())
                .code(product.getCode())
                .build();
    }

    @Override
    @Transactional
    public boolean RemoveProductByCode(String code) {

        if (this.findProductByCode(code)) {
            productRepository.deleteByCode(code);

            return true;
        } else {

            return false;
        }
    }
}
