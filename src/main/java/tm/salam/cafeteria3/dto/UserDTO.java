package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tm.salam.cafeteria3.models.Role;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "username is mandatory")
    @Size(min = 3, max = 10, message = "size username should be 5-10")
    private String username;
    @NotBlank(message = "password is mandatory")
    @Size(min = 5, max = 16, message = "size password should be 5-16")
    private String password;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email is not valid")
    private String email;
    private String imagePath;

}