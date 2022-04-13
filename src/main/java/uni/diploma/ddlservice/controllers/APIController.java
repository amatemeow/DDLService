package uni.diploma.ddlservice.controllers;

import org.springframework.web.bind.annotation.*;
import uni.diploma.ddlservice.entities.*;
import uni.diploma.ddlservice.processing.DDLBuilder;
import uni.diploma.ddlservice.processing.FileBuilder;
import uni.diploma.ddlservice.processing.JSONDeserializer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.util.*;

import static uni.diploma.ddlservice.DdlServiceApplication.webSessions;

@RestController
public class APIController {

    @PostMapping("/api/newSession")
    public static Session initiateNewSession(HttpSession session) {
        Session response = webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                .findFirst().orElse(new Session(session.getId()));
        if (!webSessions.contains(response)) {
            webSessions.add(response);
        }
        return response;
    }

//    @GetMapping("/api/schema")
//    public SQLSchema getSchema(HttpSession session) {
//        return webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
//                .findFirst().orElse(initiateNewSession(session)).getSessionSchema();
//    }

    //Optimize with Custom Exception
    @GetMapping("/api/getDDL")
    public String getDDL(HttpSession session) throws RuntimeException{
        try {
            return new DDLBuilder(webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                    .findFirst().get().getSessionSchema()).build();
        } catch (RuntimeException re) {
            throw new RuntimeException("Error: trying to build an empty model.");
        }
    }

    @PostMapping("/api/schema/new")
    public SQLSchema addSchema(@RequestBody Map<String, Object> requestBody, HttpSession session) {
        SQLSchema schema = new JSONDeserializer(requestBody).deserialize();
        webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                .findFirst().orElse(initiateNewSession(session)).associateSchema(schema);
        return schema;
    }

//    @GetMapping("/api/viewSession")
//    public Session getSession(HttpSession session) {
//        return webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
//                .findFirst().orElse(initiateNewSession(session));
//    }

    @PostMapping("/api/dropSession")
    public static void dropSession(HttpSession session) {
        webSessions.remove(webSessions.stream().filter(s -> s.getSessionID().equals(session.getId()))
                .findFirst().orElse(initiateNewSession(session)));
    }

    //Optimize Exception
    //Fix empty file
    @GetMapping("/api/download")
    public void getScript(HttpServletResponse response, HttpSession session) {
        try {
            response.setContentType("application/sql");
            response.setHeader("Content-Disposition","attachment; filename\"scriptDDL.sql\"");
            FileBuilder fb = new FileBuilder(getDDL(session), session.getId());
            Files.copy(fb.getFile().toPath(), response.getOutputStream());
            response.getOutputStream().flush();
            fb.deleteFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
