package md.orange.exchangeoffice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BALANCES")
public class Balance implements Serializable {

    private static final long serialVersionUID = 5964527031448679001L;

    @Id
    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "TMS_LAST_UPDATE", nullable = false)
    private Date tmsLastUpdate;

}
