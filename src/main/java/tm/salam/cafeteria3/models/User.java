package tm.salam.cafeteria3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import tm.salam.cafeteria3.validations.validationForPassword.ValidPassword;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "username is mandatory")
    @Size(min = 3, max = 10, message = "username size should be greater than 2 less than 11")
    private String username;
    @NotBlank(message = "password is mandatory")
    @NotNull(message = "password isn't null")
    @NotEmpty(message = "password isn't empty")
    private String password;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "image_path")
    private String imagePath;
    @CreationTimestamp
    private LocalDate created;
    @UpdateTimestamp
    private LocalDate updated;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

}