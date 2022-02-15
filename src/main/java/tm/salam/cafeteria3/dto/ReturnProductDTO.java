package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tm.salam.cafeteria3.models.Employee;
import tm.salam.cafeteria3.models.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnProductDTO {

    private int id;
    private String productName;
    private Double  sellPrice;
    private int amount;
    private LocalDateTime created;
    private Product product;
    private String employeeName;

}
