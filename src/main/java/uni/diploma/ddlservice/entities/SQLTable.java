package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SQLTable {
    @NonNull private String tableName;
    @NonNull private ArrayList<SQLColumn> columns;
    private ArrayList<SQLConstraint> constraints = new ArrayList<>();

    public SQLTable() {
        this.tableName = "";
        this.columns = new ArrayList<>(
                List.of(new SQLColumn()));
        this.constraints = new ArrayList<>(
                List.of(new SQLConstraint()));
    }

    public SQLTable(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }
}
