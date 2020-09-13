package md.orange.exchangeoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeRateDTO implements Serializable {
    private static final long serialVersionUID = 7710164534359871164L;

    private String currencyCode;
    private int amount;
    private double rate;
}
