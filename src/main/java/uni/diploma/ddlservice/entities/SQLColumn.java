package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import uni.diploma.ddlservice.enums.SQLColTypes;

@Data
@AllArgsConstructor
public class SQLColumn {
    private String name;
    private SQLColTypes type;
}
