package uni.diploma.ddlservice.entities;

import java.time.Duration;
import java.time.LocalDateTime;

public class Session {
    private final String sessionID;
    private final LocalDateTime initiateDateTime;
    private SQLSchema sessionSchema = null;

    public Session(String sessionID) {
        this.sessionID = sessionID;
        this.initiateDateTime = LocalDateTime.now();
    }

    private Session(String id, LocalDateTime initDT, SQLSchema schema) {
        this.sessionID = id;
        this.initiateDateTime = initDT;
        this.sessionSchema = schema.clone();
    }

    public LocalDateTime getInitiateDateTime() {
        return initiateDateTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void associateSchema(SQLSchema schema) {
        this.sessionSchema = schema.clone();
    }

    public boolean isObsolete() {
        return Duration.between(this.getInitiateDateTime(), LocalDateTime.now()).toDays() > 1;
    }

    public SQLSchema getSessionSchema() {
        return sessionSchema;
    }

    @Override
    public Session clone() {
        return new Session(this.sessionID, this.initiateDateTime, this.sessionSchema);
    }
}
