package tm.salam.cafeteria3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private int id;
    private String name;
    private String code;
    private int amount;
    private String imagePath;
    private Double takenPrice;
    private Double sellPrice;
    private Double sum;
}
