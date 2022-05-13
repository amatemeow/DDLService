package uni.diploma.ddlservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import uni.diploma.ddlservice.enums.SQLConTypes;

import java.util.Optional;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SQLConstraint {
    private Optional<String> name = Optional.empty();
    private SQLConTypes type;
    @NonNull private String column;
    private Optional<ForeignReference> reference = Optional.empty();
    private Optional<CheckConstraint> check = Optional.empty();

    public SQLConstraint() {
        this.name = Optional.of("");
        this.type = null;
        this.column = "";
    }

    public SQLConstraint(Optional<String> name, SQLConTypes type, String column) {
        this.name = name;
        this.type = type;
        this.column = column;
    }

    public SQLConstraint(SQLConTypes type, String column, Optional<ForeignReference> reference,
                         Optional<CheckConstraint> check) {
        this.type = type;
        this.column = column;
        this.reference = reference;
        this.check = check;
    }
}
