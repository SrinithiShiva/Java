package com.example.TaskManagement.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class UserTeam {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long userTeamId;
    private String teamName;
    private String teamDescription;
    @OneToMany(mappedBy = "userTeam", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Users> teamMembers;

    public Long getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(Long userTeamId) {
        this.userTeamId = userTeamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public List<Users> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<Users> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
