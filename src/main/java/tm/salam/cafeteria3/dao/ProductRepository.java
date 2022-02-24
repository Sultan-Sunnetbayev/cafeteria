package tm.salam.cafeteria3.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    Product getProductByCode(String code);

    void deleteByCode(String code);

    List<Product> findAll(Specification<Product> productSpecification);

    Product findByName(String name);

    Product findByCode(String code);

}
