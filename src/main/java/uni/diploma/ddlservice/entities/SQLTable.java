package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SQLTable {
    @NonNull private String tableName;
    @NonNull private ArrayList<SQLColumn> columns;
    private ArrayList<SQLConstraint> constraints;
}
