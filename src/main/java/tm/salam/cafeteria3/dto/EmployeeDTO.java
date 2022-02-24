package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private int id;
    @NotBlank(message = "employee name is mandatory")
    @Size(min = 4, max = 10, message = "employee name size should be greater than 3 less than 11")
    private String name;
    @NotBlank(message = "employee surname is mandatory")
    @Size(min = 5, max = 20, message = "employee surname size should be greater than 4 less than 21")
    private String surname;
    private String password;
    private String grade;
    private String imagePath;
    private String code;

}
