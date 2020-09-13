package md.orange.exchangeoffice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CURRENCIES")
public class Currency implements Serializable {

    private static final long serialVersionUID = 7207680733771290464L;

    @Id
    @Column(name = "CODE", nullable = false)
    @NonNull
    private String code;

}
