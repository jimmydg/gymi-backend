package com.gymi.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "activity")
@EntityListeners(AuditingEntityListener.class)
public class Activity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sessionId")
    private long sessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipmentId")
    private ActivityType activityType;

    @Column
    private Timestamp dateTime;

    @Column
    private Long amount;

    @Column
    private long sessionTimes;

    @Transient
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSessionTimes() {
        return sessionTimes;
    }

    public void setSessionTimes(long sessionTimes) {
        this.sessionTimes = sessionTimes;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
