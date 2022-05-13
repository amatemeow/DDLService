package uni.diploma.ddlservice.enums;

public enum SQLCheckTypes {
    LIKE("LIKE"),
    EQUALS("="),
    NOTEQUALS("<>"),
    GREATER(">"),
    SMALLER("<");

    private final String representation;

    SQLCheckTypes(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    public String toType() {
        return super.toString();
    }
}
