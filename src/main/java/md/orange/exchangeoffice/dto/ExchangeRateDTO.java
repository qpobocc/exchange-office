package md.orange.exchangeoffice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO implements Serializable {
    private static final long serialVersionUID = 3479735403800773429L;

    private String currencyCode;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date rateDate;
    private int amount;
    private double rate;
}
