package tm.salam.cafeteria3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tm.salam.cafeteria3.dao.ProductRepository;
import tm.salam.cafeteria3.dao.SpoiledProductRepository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.dto.SpoiledProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SpoiledProduct;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpoiledProductServiceImpl implements SpoiledProductService {

    private final SpoiledProductRepository spoiledProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SpoiledProductServiceImpl(SpoiledProductRepository spoiledProductRepository,
                                     ProductRepository productRepository) {
        this.spoiledProductRepository = spoiledProductRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAddedProducts() {

        List<SpoiledProduct> spoiledProducts = spoiledProductRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (SpoiledProduct spoiledProduct : spoiledProducts) {
            productDTOS.add(ProductDTO.builder()
                    .id(spoiledProduct.getId())
                    .name(spoiledProduct.getProductName())
                    .amount(spoiledProduct.getAmount())
                    .build());
        }

        return productDTOS;
    }

    @Override
    @Transactional
    public boolean AddProducts(List<ProductDTO> productDTOS) {


        for (ProductDTO productDTO : productDTOS) {

            Product product = null;
            SpoiledProduct spoiledProduct = null;

            product = productRepository.getProductByCode(productDTO.getCode());

            if (product != null) {

                spoiledProduct = new SpoiledProduct();
                spoiledProduct.setProductName(product.getName());
                spoiledProduct.setProduct(product);
                spoiledProduct.setSellPrice(product.getSellPrice());
                if (product.getAmount() >= productDTO.getAmount()) {
                    spoiledProduct.setAmount(productDTO.getAmount());
                    product.setAmount(product.getAmount() - productDTO.getAmount());
                } else {
                    spoiledProduct.setAmount(product.getAmount());
                    product.setAmount(0);
                }
            }
            if (spoiledProduct != null) {
                spoiledProductRepository.save(spoiledProduct);
            }

        }

        return true;
    }

    @Override
    public boolean findProductById(int id) {

        SpoiledProduct spoiledProduct = spoiledProductRepository.findByProduct_Id(id);

        if (spoiledProduct == null) {

            return false;
        } else {

            return true;
        }
    }

    @Override
    public SpoiledProduct getSpoiledProductById(int id) {

        return spoiledProductRepository.findByProduct_Id(id);
    }

}
