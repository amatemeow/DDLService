package uni.diploma.ddlservice.processing;

import uni.diploma.ddlservice.entities.*;

import java.util.Optional;

public class DDLBuilder {
    private final StringBuilder DDL = new StringBuilder();
    private final SQLSchema sourceSchema; //If initial object gone missing while executing

    public DDLBuilder(SQLSchema schema) {
        this.sourceSchema = schema.clone(); //Since we want new reference
    }

    public String build() {
        boolean cased = false;
        if (sourceSchema.getSqlSchema().isPresent()) {
            String sqlSchemaName = sourceSchema.getSqlSchema().get();

            for (SQLTable table : sourceSchema.getTables()) {
                table.setTableName("\"" + sqlSchemaName + "\".\"" + table.getTableName() + "\"");

                for (SQLConstraint constraint : table.getConstraints()) {
                    if (constraint.getName().isPresent()) {
                        constraint.setName(Optional.of("\"" + sqlSchemaName + "\".\"" +
                                constraint.getName().get() + "\""));
                    }
                }
            }
            cased = true;
        }

        for (SQLTable table : sourceSchema.getTables()) {
            String createCurrent = "\n\n/*Creating table " + table.getTableName() + "*/" +
                    "\nCREATE TABLE " + (cased ? "" : "\"") + table.getTableName() + (cased ? "" : "\"") + " \n(";

            for (SQLColumn column : table.getColumns()) {
                createCurrent += "\"" + column.getName() + "\" " + column.getType() + ", \n";
            }
            createCurrent = createCurrent.substring(0, createCurrent.lastIndexOf(", \n"));

            for (SQLConstraint constraint : table.getConstraints()) {
                createCurrent += ", \n" + "CONSTRAINT " + (cased && constraint.getName().isPresent() ? "" : "\"") +
                        constraint.getName().orElse("") +
                        (cased && constraint.getName().isPresent() ? "" : "\"") + " " +
                        constraint.getType().toString() + " (\"" + constraint.getColumn() + "\")";

                if (constraint.getReference().isPresent()) {
                    ForeignReference reference = constraint.getReference().get();
                    createCurrent += " REFERENCES \"" + reference.getReferenceTable() +
                            "\" (\"" + reference.getReferenceColumn() + "\")";
                }
            }
            createCurrent += ");";
            DDL.append(createCurrent);
        }

        return DDL.toString();
    }
}
