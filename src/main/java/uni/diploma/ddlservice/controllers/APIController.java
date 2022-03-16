package uni.diploma.ddlservice.controllers;

import org.springframework.web.bind.annotation.*;
import uni.diploma.ddlservice.entities.SQLColumn;
import uni.diploma.ddlservice.entities.SQLConstraint;
import uni.diploma.ddlservice.entities.SQLSchema;
import uni.diploma.ddlservice.entities.SQLTable;
import uni.diploma.ddlservice.enums.SQLColTypes;
import uni.diploma.ddlservice.enums.SQLConTypes;
import uni.diploma.ddlservice.exceptions.DDLServiceMissingUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class APIController {

    private static final SQLSchema sampleSchema = new SQLSchema(1L, "exampleUser", "Sample_Schema",
            new ArrayList<>(List.of(new SQLTable("MY_FIRST_TABLE",
            new ArrayList<>(List.of(new SQLColumn("ID", SQLColTypes.NUMBER))),
                    new ArrayList<>(List.of(
                            new SQLConstraint("MY_ID_UNQ", SQLConTypes.UNIQUE, "ID"),
                            new SQLConstraint("MY_ID_NOTNULL", SQLConTypes.NOTNULL, "ID")))))));
    private static final ArrayList<SQLSchema> schemas = new ArrayList<>(List.of(sampleSchema));

    @GetMapping("/api/hello")
    public String getHello() {
        return "Hello!";
    }

    @GetMapping("/api/schema/{user}")
    public SQLSchema getUserSchema(@PathVariable String user)
            throws DDLServiceMissingUserException {
        try {
            return schemas.stream().filter(sch -> sch.getUser().equals(user)).findFirst().get();
        } catch (NoSuchElementException NSE) {
            throw new DDLServiceMissingUserException();
        }
    }

    @GetMapping("/api/ddl-from-schema/{id}")
    public String getDDL(@PathVariable Long id) {
        return "HUY";
    }

    @PostMapping("/api/schema/new")
    public String addSchema(@RequestBody Map<String, Object> requestBody) {
        return "newHUY";
    }
}
