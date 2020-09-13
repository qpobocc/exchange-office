package md.orange.exchangeoffice.dto;


import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = -3982755044738444016L;

    @ApiParam(example = "crm001")
    private String code;
    private String fullName;
}
