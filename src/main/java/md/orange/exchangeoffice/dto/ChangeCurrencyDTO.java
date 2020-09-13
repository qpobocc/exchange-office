package md.orange.exchangeoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCurrencyDTO {

    private String fromCurrencyCode;
    private double fromCurrencyAmount;
    private String toCurrencyCode;
    private String employeeCode;

}
