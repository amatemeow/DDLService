package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import uni.diploma.ddlservice.enums.SQLConTypes;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SQLConstraint {
    @NonNull private String name;
    @NonNull private SQLConTypes type;
    @NonNull private String column;
    private String reference;
}
