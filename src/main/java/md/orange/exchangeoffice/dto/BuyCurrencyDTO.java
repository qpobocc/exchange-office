package md.orange.exchangeoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyCurrencyDTO {

    private String currencyCode;
    private double amount;
    private double rate;
    private String employeeCode;

}
