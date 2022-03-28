package uni.diploma.ddlservice.enums;

public enum SQLColTypes {
    NUMBER("NUMBER"),
    FLOAT("FLOAT"),
    CHAR("CHAR"),
    VARCHAR2("VARCHAR2(4000)"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP");

    private final String representation;

    SQLColTypes(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
