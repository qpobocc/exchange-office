package md.orange.exchangeoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUpdateDTO implements Serializable {
    private static final long serialVersionUID = -5315346214978265306L;

    private String currencyCode;
    private Double newBalanceAmount;
}
