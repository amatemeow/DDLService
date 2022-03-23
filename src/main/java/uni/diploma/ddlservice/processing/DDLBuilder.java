package uni.diploma.ddlservice.processing;

import uni.diploma.ddlservice.entities.*;
import uni.diploma.ddlservice.enums.SQLConTypes;

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

        /*Если указан пользователь БД (схема), и будет стоять галочка создания от лица администратора,
        * тогда будет сгенерирован код на создание схемы этого пользователя и выделение ему основных привелегий
        * для работы с базой*/
        if (sourceSchema.isByRoot() && sourceSchema.getSqlSchema().isPresent()) {
            DDL.append(String.format("\n\n/*Creating a specified schema %1$s with password %1$s*/",
                    sourceSchema.getSqlSchema().get()));
            DDL.append(String.format("\nCREATE USER %1$s IDENTIFIED BY %1$s;", sourceSchema.getSqlSchema().get()));
            DDL.append(String.format("\nGRANT CREATE SESSION TO %s;", sourceSchema.getSqlSchema().get()));
            DDL.append(String.format("\nGRANT CONNECT TO %s;", sourceSchema.getSqlSchema().get()));
        }

        for (SQLTable table : sourceSchema.getTables()) {
            String createCurrent = "\n\n/*Creating table " + table.getTableName() + "*/" +
                    "\nCREATE TABLE " + (cased ? "" : "\"") + table.getTableName() + (cased ? "" : "\"") + " \n(";

            for (SQLColumn column : table.getColumns()) {
                createCurrent += "\"" + column.getName() + "\" " + column.getType().toString() + ", \n";
            }
            createCurrent = createCurrent.substring(0, createCurrent.lastIndexOf(", \n"));

            /*NOT NULL ограничение будет закодировано как CHECK ограничение с условием IS NOT NULL*/
            for (SQLConstraint constraint : table.getConstraints()) {
                createCurrent += ", \n" + "CONSTRAINT " + (cased && constraint.getName().isPresent() ? "" : "\"") +
                        constraint.getName().orElse("") +
                        (cased && constraint.getName().isPresent() ? "" : "\"") + " " +
                        (SQLConTypes.NOTNULL == constraint.getType()
                                ? SQLConTypes.CHECK.toString() + " (\"" + constraint.getColumn() + "\" IS NOT NULL)"
                                : constraint.getType().toString() + " (\"" + constraint.getColumn() + "\")");

                if (constraint.getReference().isPresent()) {
                    ForeignReference reference = constraint.getReference().get();
                    createCurrent += " REFERENCES \"" + (sourceSchema.getSqlSchema().isPresent()
                                    ? sourceSchema.getSqlSchema().get() + "\".\"" : "") +
                            reference.getReferenceTable() + "\" (\"" + reference.getReferenceColumn() + "\")";
                }
            }
            createCurrent += ");";
            DDL.append(createCurrent);
        }

        return DDL.toString();
    }
}
