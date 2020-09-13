package md.orange.exchangeoffice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CURRENCY_EXCHANGES")
public class CurrencyExchange implements Serializable {

    private static final long serialVersionUID = 7849698218821971519L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IN_CUR_CODE", nullable = false)
    private Currency receivedCurrency;

    @Column(name = "IN_CUR_AMOUNT", nullable = false)
    private Double receivedCurrencyAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OUT_CUR_CODE", nullable = false)
    private Currency releasedCurrency;

    @Column(name = "OUT_CUR_AMOUNT", nullable = false)
    private Double releasedCurrencyAmount;

    @Column(name = "EXCHANGE_RATE", nullable = false)
    private Double exchangeRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_CODE", nullable = false)
    private Employee employee;

    @Column(name = "EXCHANGE_DATE", nullable = false)
    private Date exchangeDate;


}
