package md.orange.exchangeoffice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXCHANGE_RATES")
public class ExchangeRate implements Serializable {

    private static final long serialVersionUID = -2375310512168194141L;

    @EmbeddedId
    private ExchangeRateId id;
    @Column(name = "AMOUNT", nullable = false)
    private int amount;
    @Column(name = "RATE", nullable = false)
    private double rate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class ExchangeRateId implements Serializable {
        private static final long serialVersionUID = -5709058438662609845L;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "CURRENCY_CODE", nullable = false)
        private Currency currency;

        @Column(name = "RATE_DATE", nullable = false)
        @Temporal(TemporalType.DATE)
        private Date rateDate;
    }

}
