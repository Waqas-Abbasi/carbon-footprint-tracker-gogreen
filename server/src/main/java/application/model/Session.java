package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spring_session")
public class Session {

    @Id
    @Column(name = "primary_id")
    private String primaryId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "last_access_time")
    private String lastAccessTime;

    @Column(name = "max_inactive_interval")
    private String maxInactiveInterval;

    @Column(name = "expiry_time")
    private String expiryTime;

    @Column(name = "principal_name")
    private String username;

    public Session() {
    }

    public Session(String sessionId, String username) {
        this.sessionId = sessionId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
