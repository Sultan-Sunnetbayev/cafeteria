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
@Table(name = "spoiled_products")
public class SpoiledProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Double sellPrice;
    private int amount;
    @CreationTimestamp
    private LocalDateTime created;
    @OneToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

}
