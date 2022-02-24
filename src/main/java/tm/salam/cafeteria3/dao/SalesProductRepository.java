package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.dto.ProductDTO;
import tm.salam.cafeteria3.models.Product;
import tm.salam.cafeteria3.models.SalesProduct;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface SalesProductRepository extends JpaRepository<SalesProduct, Integer> {

    List<SalesProduct> findByEmployeeId(int id);

    List<SalesProduct> findByEmployee_IdAndProduct_IdAndAmountGreaterThan(int employeeId, int productId, int amount);

    List<SalesProduct> findByCreated(LocalDate date);
}
