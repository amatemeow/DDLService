package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForeignReference {
    private String referenceTable;
    private String referenceColumn;
}
