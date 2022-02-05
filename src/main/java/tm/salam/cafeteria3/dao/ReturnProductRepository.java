package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.ReturnProduct;

@Repository
public interface ReturnProductRepository extends JpaRepository<ReturnProduct,Integer> {
}
