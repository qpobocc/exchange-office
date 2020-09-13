package md.orange.exchangeoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCurrencyDTO implements Serializable {
    private static final long serialVersionUID = 3747612498315179215L;

    private String currencyCode;
    private Double amountAvailable;
}
