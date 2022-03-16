package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SQLSchema {
    @NonNull private final Long id;
    @NonNull private final String user;
    private String schemaName = "Untitled";
    private String sqlSchema;
    @NonNull private ArrayList<SQLTable> tables;

    public SQLSchema(Long id, String user, String schemaName, ArrayList<SQLTable> tables) {
        this.id = id;
        this.user = user;
        this.schemaName = schemaName;
        this.tables = (ArrayList<SQLTable>) tables.clone();
    }
}
