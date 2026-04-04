package com.example.TaskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManagement.dto.TeamCreateDTO;
import com.example.TaskManagement.dto.TeamInviteDTO;
import com.example.TaskManagement.dto.TeamResponseDTO;
import com.example.TaskManagement.entity.UserTeam;
import com.example.TaskManagement.service.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamCreateDTO createDTO) {
        UserTeam team = teamService.createTeam(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toTeamResponse(team));
    }

    @PostMapping("/{teamId}/invite")
    public ResponseEntity<TeamResponseDTO> inviteMember(@PathVariable Long teamId, @RequestBody TeamInviteDTO inviteDTO) {
        UserTeam team = teamService.inviteMember(teamId, inviteDTO.getUserId());
        return ResponseEntity.ok(toTeamResponse(team));
    }

    private TeamResponseDTO toTeamResponse(UserTeam team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setTeamId(team.getUserTeamId());
        dto.setTeamName(team.getTeamName());
        dto.setTeamDescription(team.getTeamDescription());
        return dto;
    }
}
