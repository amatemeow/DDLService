package uni.diploma.ddlservice.enums;

public enum SQLColTypes {
    NUMBER("NUMBER", "INTEGER"),
    FLOAT("FLOAT", "FLOAT"),
    CHAR("CHAR", "CHARACTER"),
    VARCHAR2("VARCHAR2(4000)", "STRING"),
    DATE("DATE", "DATE"),
    TIMESTAMP("TIMESTAMP", "DATE WITH TIME");

    private final String representation;
    private final String casual;

    SQLColTypes(String representation, String casual) {
        this.representation = representation;
        this.casual = casual;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    public String toType() {
        return super.toString();
    }

    public String toCasual() {
        return this.representation.equals(this.casual)
                ? this.representation : this.casual + " (" + this.representation + ")";
    }
}
