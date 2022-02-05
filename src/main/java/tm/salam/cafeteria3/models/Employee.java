package tm.salam.cafeteria3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "name")
    private String name;
    private String surname;
    private String password;
    private String email;
    private String grade;
    @Column(name = "image_path")
    private String imagePath;
    private String code;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    LocalDateTime created;
    @OneToOne(optional = true,mappedBy = "employee")
    private SalesProduct salesProduct;
    @OneToOne(optional =true, mappedBy = "employee")
    private ReturnProduct returnProduct;
}
