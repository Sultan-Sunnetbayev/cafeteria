package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.SpoiledProduct;

@Repository
public interface SpoiledProductRepository extends JpaRepository<SpoiledProduct,Integer> {

    SpoiledProduct findByProduct_Id(int productId);
}
