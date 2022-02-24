package tm.salam.cafeteria3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "employee name is mandatory")
    @Size(min = 4, max = 10, message = "employee name size should be greater than 3 less than 11")
    @Column(name = "name")
    private String name;
    @NotBlank(message = "employee surname is mandatory")
    @Size(min = 5, max = 20, message = "employee surname size should be greater than 4 less than 21")
    private String surname;
    //    @Size(min = 5,max = 16,message = "password size should be greater than 4 less than 17")
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "grade is mandatory")
    private String grade;
    @Column(name = "image_path")
    private String imagePath;
    private String code;
    @CreationTimestamp
    private LocalDate created;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<SalesProduct> salesProducts;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ReturnProduct> returnProducts;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Bucket> buckets;

}