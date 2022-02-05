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
    private BigDecimal amount;
    private String imagePath;
    private BigDecimal takenPrice;
    private BigDecimal sellPrice;

}
