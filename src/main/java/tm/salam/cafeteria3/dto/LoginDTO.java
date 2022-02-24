package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tm.salam.cafeteria3.models.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    private String username;
    private String email;
    private List<String>roles;
}
