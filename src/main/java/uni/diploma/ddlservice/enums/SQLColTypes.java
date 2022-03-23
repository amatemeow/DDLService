package uni.diploma.ddlservice.enums;

public enum SQLColTypes {
    NUMBER("NUMBER"),
    FLOAT("FLOAT"),
    CHAR("CHAR"),
    VARCHAR2("VARCHAR2(4000)"),
    CLOB("CLOB"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP"),
    RAW("RAW(2000)"),
    BLOB("BLOB");

    private String representation;

    SQLColTypes(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
