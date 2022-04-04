package uni.diploma.ddlservice.enums;

public enum SQLConTypes {
    UNIQUE("UNIQUE"),
    CHECK("CHECK"),
    NOTNULL("NOT NULL"),
    PRIMARY_KEY("PRIMARY KEY"),
    FOREIGN_KEY("FOREIGN KEY");

    private final String representation;

    SQLConTypes(String representation) {
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
