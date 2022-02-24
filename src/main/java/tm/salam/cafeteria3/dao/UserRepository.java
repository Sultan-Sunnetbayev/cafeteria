package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.salam.cafeteria3.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findFirstByUsername(String username);

    User findById(int id);

    User findFirstById(int id);

    User findByEmail(String email);

    User findFirstByEmail(String email);
}
