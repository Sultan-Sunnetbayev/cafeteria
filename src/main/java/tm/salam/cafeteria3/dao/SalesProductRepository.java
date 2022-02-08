package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SalesProduct;

@Repository
public interface SalesProductRepository extends JpaRepository<SalesProduct,Integer> {
    
}
