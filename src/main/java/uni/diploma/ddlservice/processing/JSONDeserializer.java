package uni.diploma.ddlservice.processing;

import uni.diploma.ddlservice.entities.*;
import uni.diploma.ddlservice.enums.SQLColTypes;
import uni.diploma.ddlservice.enums.SQLConTypes;

import java.util.*;

public class JSONDeserializer {
    private final Map<String, Object> source;

    public JSONDeserializer(Map<String, Object> requestBody) {
        this.source = new HashMap<>(requestBody);
    }

    public SQLSchema deserialize() {
        SQLSchema requestSchema = new SQLSchema((String) source.get("user"));
        requestSchema.setSchemaName((String) source.get("schemaName"));
        requestSchema.setSqlSchema(Optional.ofNullable((String) source.get("sqlSchema")));
        requestSchema.setByRoot((boolean) source.get("byRoot"));
        ArrayList<LinkedHashMap<String, Object>> requestTables = (ArrayList<LinkedHashMap<String, Object>>) source.get("tables");

        ArrayList<SQLTable> tables = new ArrayList<>();
        for (LinkedHashMap<String, Object> table : requestTables) {
            ArrayList<LinkedHashMap<String, Object>> requestColumns =
                    (ArrayList<LinkedHashMap<String, Object>>) table.get("columns");
            ArrayList<LinkedHashMap<String, Object>> requestConstraints =
                    (ArrayList<LinkedHashMap<String, Object>>) table.get("constraints");
            ArrayList<SQLColumn> columns = new ArrayList<>();
            ArrayList<SQLConstraint> constraints = new ArrayList<>();

            for (LinkedHashMap<String, Object> column : requestColumns) {
                columns.add(new SQLColumn((String) column.getOrDefault("name", ""),
                        column.get("type") == null ? null : SQLColTypes.valueOf((String) column.get("type"))));
            }

            for (LinkedHashMap<String, Object> constraint : requestConstraints) {
                ForeignReference reference = null;
                if (constraint.get("reference") != null) {
                    LinkedHashMap<String, Object> requestReference =
                            (LinkedHashMap<String, Object>) constraint.get("reference");
                    reference = new ForeignReference((String) requestReference.get("referenceTable"),
                            (String) requestReference.get("referenceColumn"));
                }

                constraints.add(new SQLConstraint(Optional.ofNullable((String) constraint.get("name")),
                        constraint.get("type") == null ? null : SQLConTypes.valueOf((String) constraint.get("type")),
                        (String) constraint.getOrDefault("column", ""), Optional.ofNullable(reference)));
            }
            tables.add(new SQLTable((String) table.get("tableName"), columns, constraints));
        }
        requestSchema.setTables(tables);

        return requestSchema;
    }
}
