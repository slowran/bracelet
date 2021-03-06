package org.bracelet.entity;

import net.sf.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

/**
 * 状态
 */
@Entity
@Table(name = "state")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stateType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("base")
public class State {
    /**
     * 状态ID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 状态起始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime", nullable = false)
    private Date startTime;

    /**
     * 状态终止时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entTime", nullable = false)
    private Date endTime;

    /**
     * 状态类型（sport、heart、sleep）
     */
    @Column(name = "status", length = 20, nullable = false)
    private String status;

    /**
     * 状态所属用户ID
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    public State() {

    }

    public State(String jsonString, User user) {
        JSONObject json = JSONObject.fromString(jsonString);
        if (json.has("id")) {
            this.id = json.getLong("id");
        } else {
            this.id = null;
        }
        this.startTime = new Date(json.getLong("startTime"));
        this.endTime = new Date(json.getLong("endTime"));
        this.status = json.getString("status");
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("startTime", startTime.getTime())
                .put("endTime", endTime.getTime())
                .put("status", status);
        if (id != null) {
            json.put("id", id);
        }
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (id != null ? !id.equals(state.id) : state.id != null) return false;
        if (startTime != null ? !startTime.equals(state.startTime) : state.startTime != null) return false;
        if (endTime != null ? !endTime.equals(state.endTime) : state.endTime != null) return false;
        if (status != null ? !status.equals(state.status) : state.status != null) return false;
        if (user != null ? !user.equals(state.user) : state.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
