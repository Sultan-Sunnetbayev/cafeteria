package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SalesProduct;

import java.util.List;

@Repository
public interface SalesProductRepository extends JpaRepository<SalesProduct,Integer> {

    List<SalesProduct> findByEmployeeId(int id);
}
