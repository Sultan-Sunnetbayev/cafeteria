package tm.salam.cafeteria3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "return_products")
public class ReturnProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Double sellPrice;
//    private Double sum;
    private int amount;
    @CreationTimestamp
    private LocalDateTime created;
    @OneToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToOne(optional =true,cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @OneToOne(optional =true,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}