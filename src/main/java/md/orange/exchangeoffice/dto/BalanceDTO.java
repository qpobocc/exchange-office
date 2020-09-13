package md.orange.exchangeoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceDTO implements Serializable {
    private static final long serialVersionUID = 5457585028163767836L;

    private String currencyCode;
    private Double amount;
    private Date tmsLastUpdate;

}
