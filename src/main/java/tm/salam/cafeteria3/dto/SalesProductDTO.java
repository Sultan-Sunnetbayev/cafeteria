package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesProductDTO {

    private int id;
    private String productName;
    private BigDecimal price;
    private BigDecimal amount;
    private String imagePath;
    private LocalDateTime created;
    private Product product;
    private Employee employee;

}
