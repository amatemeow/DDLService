package uni.diploma.ddlservice.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Session {
    private final String sessionID;
    private final LocalDateTime initiateDateTime;
    private final ArrayList<SQLSchema> sessionSchemas = new ArrayList<>();

    public Session(String sessionID) {
        this.sessionID = sessionID;
        this.initiateDateTime = LocalDateTime.now();
    }

    public LocalDateTime getInitiateDateTime() {
        return initiateDateTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public boolean associateSchema(SQLSchema schema) {
        if (this.sessionSchemas.contains(schema)) {
            return false;
        }
        this.sessionSchemas.add(schema);
        return true;
    }

    public boolean isObsolete() {
        return Duration.between(this.getInitiateDateTime(), LocalDateTime.now()).toDays() > 3;
    }

    public ArrayList<SQLSchema> getSessionSchemas() {
        return sessionSchemas;
    }
}
