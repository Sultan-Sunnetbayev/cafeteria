package tm.salam.cafeteria3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    private LocalDate created;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SalesProduct> salesProducts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ReturnProduct> returnProducts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SpoiledProduct> spoiledProducts;
    @OneToOne(mappedBy = "product", cascade = CascadeType.DETACH)
    private Bucket bucket;


}