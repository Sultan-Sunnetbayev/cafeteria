package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Bucket;
import tm.salam.cafeteria3.models.Product;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Integer> {

    Bucket findBucketByProduct_Id(int id);

    Bucket findById(int id);

    void deleteAll();
    
}
