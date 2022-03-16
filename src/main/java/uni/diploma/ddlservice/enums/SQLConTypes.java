package uni.diploma.ddlservice.enums;

public enum SQLConTypes {
    UNIQUE("UNIQUE"),
    NOTNULL("NOT NULL"),
    PRIMARY_KEY("PRIMARY KEY");

    private String representation;

    SQLConTypes(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
