package tm.salam.cafeteria3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tm.salam.cafeteria3.models.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
