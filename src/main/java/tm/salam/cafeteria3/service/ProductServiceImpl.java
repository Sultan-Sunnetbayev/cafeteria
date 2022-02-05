package tm.salam.cafeteria3.service;

import lombok.Builder.ObtainVia;
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

        Product product=Product.builder()
                .imagePath(productDTO.getImagePath())
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .takenPrice(productDTO.getTakenPrice())
                .sellPrice(productDTO.getSellPrice())
                .code(productDTO.getCode())
                .build();

        productRepository.save(product);
    }

//    @Override
//    public void EditProduct(ProductDTO productDTO) {
//
//        Product product=productRepository.findById(productDTO.getId());
//
//        if(product==null){
//
//            throw new RuntimeException("Product not found by name "+productDTO.getName());
//        }
//
//        product.setName(productDTO.getName());
//        product.setImagePath(productDTO.getImagePath());
//        product.setAmount(productDTO.getAmount());
//        product.setTakenPrice(productDTO.getTakenPrice());
//        product.setSellPrice(product.getSellPrice());
//        product.setCode(productDTO.getCode());
//
//        productRepository.save(product);
//
//    }
}
