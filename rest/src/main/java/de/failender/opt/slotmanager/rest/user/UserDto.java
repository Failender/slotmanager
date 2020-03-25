package de.failender.opt.slotmanager.rest.user;

import de.failender.opt.slotmanager.persistance.user.UserEntity;

import java.time.LocalDateTime;

public class UserDto {

    private Long id;
    private String name;
    private String password;
    private String discordId;
    private String teamspeakId;
    private LocalDateTime createdDate;

    public UserDto(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.createdDate = userEntity.getCreatedDate();
    }

    public UserDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
