package uni.diploma.ddlservice.controllers;

import org.springframework.web.bind.annotation.*;
import uni.diploma.ddlservice.entities.*;
import uni.diploma.ddlservice.enums.SQLColTypes;
import uni.diploma.ddlservice.enums.SQLConTypes;
import uni.diploma.ddlservice.exceptions.DDLServiceMissingUserException;
import uni.diploma.ddlservice.processing.DDLBuilder;
import uni.diploma.ddlservice.processing.JSONDeserializer;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@RestController
public class APIController {

    private static final ConcurrentLinkedQueue<Session> webSessions = new ConcurrentLinkedQueue<>();

//    private static final SQLSchema sampleSchema = new SQLSchema("exampleUser", "Sample_Schema",
//            new ArrayList<>(List.of(new SQLTable("MY_FIRST_TABLE",
//            new ArrayList<>(List.of(new SQLColumn("ID", SQLColTypes.NUMBER))),
//                    new ArrayList<>(List.of(
//                            new SQLConstraint(Optional.of("MY_ID_UNQ"), SQLConTypes.UNIQUE, "ID"),
//                            new SQLConstraint(Optional.of("MY_ID_NOTNULL"), SQLConTypes.NOTNULL, "ID")))))),
//            true);
//    private static final ArrayList<SQLSchema> schemas = new ArrayList<>(List.of(sampleSchema));

    @GetMapping("/api/newSession")
    public Session initiateNewSession(HttpSession session) {
        Session response = new Session(session.getId());
        webSessions.add(response);
        return response;
    }

    //Needs to be implemented
    @GetMapping("/api/schema")
    public SQLSchema getUserSchema(@RequestParam(required = false) String user)
            throws DDLServiceMissingUserException {
//        try {
//            response = schemas.stream().filter(sch -> sch.getUser().equals(user))
//                    .collect(Collectors.toList());
//        } catch (RuntimeException re) {
//            throw new DDLServiceMissingUserException();
//        } finally {
//            if (response.isEmpty()) {
//                throw new DDLServiceMissingUserException();
//            }
//        }

        return new SQLSchema("");
    }

    //Needs to be implemented
    @GetMapping("/api/ddl-from-schema")
    public String getDDL(@PathVariable Long id) {
        return new DDLBuilder(new SQLSchema("")).build();
    }

    @PostMapping("/api/schema/new")
    public String addSchema(@RequestBody Map<String, Object> requestBody, HttpSession session) {
        SQLSchema schema = new JSONDeserializer(requestBody).deserialize();
        //May be optimized with inline check
        if (webSessions.stream().anyMatch(s -> s.getSessionID().equals(session.getId())))
        {
            webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                    .findFirst().get().associateSchema(schema);
        }

        return new DDLBuilder(schema).build();
    }

    @GetMapping("/api/viewSession")
    public Session getSession(HttpSession session) {
        return webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                .findFirst().orElse(null);
    }
}
