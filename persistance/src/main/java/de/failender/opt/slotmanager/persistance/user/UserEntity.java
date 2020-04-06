package de.failender.opt.slotmanager.persistance.user;

import de.failender.opt.slotmanager.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
public class UserEntity extends BaseEntity {

    private String name;
    private String password;
    private String discordId;
    private String teamspeakId;
    private LocalDateTime createdDate;
    //The latest event this user was informed about
    private Long latestEvent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public String getTeamspeakId() {
        return teamspeakId;
    }

    public void setTeamspeakId(String teamspeakId) {
        this.teamspeakId = teamspeakId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLatestEvent() {
        return latestEvent;
    }

    public void setLatestEvent(Long latestEvent) {
        this.latestEvent = latestEvent;
    }
}
