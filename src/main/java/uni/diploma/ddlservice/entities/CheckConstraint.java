package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import uni.diploma.ddlservice.enums.SQLCheckTypes;

@Data
@AllArgsConstructor
public class CheckConstraint {
    private SQLCheckTypes type;
    private String expression;
}
