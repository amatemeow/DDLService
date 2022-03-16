package uni.diploma.ddlservice.entities;

import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SQLSchema {
    private final UUID id = UUID.randomUUID();
    @NonNull private final String user;
    private String schemaName = "Untitled";
    private Optional<String> sqlSchema = Optional.empty();
    @NonNull private ArrayList<SQLTable> tables;

    public SQLSchema(String user) {
        this.user = user;
    }

    public SQLSchema(String user, String schemaName, ArrayList<SQLTable> tables) {
        this.user = user;
        this.schemaName = schemaName;
        this.tables = new ArrayList<>(tables);
    }

    @Override
    public SQLSchema clone() {
        return new SQLSchema(this.user, this.schemaName, this.sqlSchema, this.tables);
    }
}
