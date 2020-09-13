package md.orange.exchangeoffice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

    private static final long serialVersionUID = -1561580439386487673L;

    @Id
    @Column(name = "CODE", nullable = false)
    @NonNull
    private String code;

    @Column(name = "FULL_NAME")
    private String fullName;

}
