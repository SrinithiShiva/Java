package com.example.TaskManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TaskManagement.dto.TeamCreateDTO;
import com.example.TaskManagement.entity.UserTeam;
import com.example.TaskManagement.entity.Users;
import com.example.TaskManagement.repository.UserRepository;
import com.example.TaskManagement.repository.UserTeamRepository;

@Service
public class TeamService {

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private UserRepository userRepository;

    public UserTeam createTeam(TeamCreateDTO createDTO) {
        UserTeam team = new UserTeam();
        team.setTeamName(createDTO.getTeamName());
        team.setTeamDescription(createDTO.getTeamDescription());
        return userTeamRepository.save(team);
    }

    public UserTeam inviteMember(Long teamId, Long userId) {
        UserTeam team = userTeamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserTeam(team);
        userRepository.save(user);
        return team;
    }
}
