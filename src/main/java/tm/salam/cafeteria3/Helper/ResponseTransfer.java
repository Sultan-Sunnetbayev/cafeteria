package tm.salam.cafeteria3.Helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTransfer {

    private String message;
    private Boolean status;
}
