package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findFirstByUsername(String username);
    Admin findById(int id);
}
