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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String code;
    private int amount;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "taken_price")
    private Double takenPrice;
    @Column(name = "sell_price")
    private Double sellPrice;
    @CreationTimestamp
    private LocalDateTime created;
    @OneToOne(optional = true,mappedBy = "product")
    private SalesProduct salesProduct;
    @OneToOne(optional = true,mappedBy = "product")
    private ReturnProduct returnProduct;
    @OneToOne(optional = true,mappedBy = "product")
    private SpoiledProduct spoiledProduct;
    @OneToOne(optional = true,mappedBy = "product")
    private Bucket bucket;
}