package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findById(int id);
}
