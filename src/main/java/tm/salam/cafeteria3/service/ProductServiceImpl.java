package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO>getAllProducts(){

        return productRepository.findAll().stream()
                .map((this::toDTO))
                .collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {

        return ProductDTO.builder()
                .imagePath(product.getImagePath())
                .name(product.getName())
                .amount(product.getAmount())
                .takenPrice(product.getTakenPrice())
                .sellPrice(product.getSellPrice())
                .code(product.getCode())
                .build();
    }

    @Override
    public void AddOrEditProduct(ProductDTO productDTO){

        Product product=productRepository.getProductByCode(productDTO.getCode());

        if(product != null){

            product.setImagePath(productDTO.getImagePath());
            product.setName(productDTO.getName());
            product.setTakenPrice(productDTO.getTakenPrice());
            product.setSellPrice(productDTO.getSellPrice());
            product.setAmount(productDTO.getAmount());
            product.setCode(productDTO.getCode());

        }else {
            product=Product.builder()
                .imagePath(productDTO.getImagePath())
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .takenPrice(productDTO.getTakenPrice())
                .sellPrice(productDTO.getSellPrice())
                .code(productDTO.getCode())
                .build();
        }
        productRepository.save(product);
    }

    @Override
    public boolean findProductByCode(String code) {

        Product product=productRepository.getProductByCode(code);

        if(product==null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Product getProductByCode(String code) {

        return productRepository.getProductByCode(code);
    }
}
