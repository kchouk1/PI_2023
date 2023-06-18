package tn.esprit.pi_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Team;

import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.TeamRepository;
import tn.esprit.pi_backend.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }


    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team team) {
        Optional<Team> existingTeamOptional = teamRepository.findById(id);

        if (existingTeamOptional.isPresent()) {
            Team existingTeam = existingTeamOptional.get();
            existingTeam.setName(team.getName());
            existingTeam.setUsers(team.getUsers());
            return teamRepository.save(existingTeam);
        } else {
            throw new RuntimeException("Team not found");
        }
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }


//    public void addUserToTeam(Long teamId, Long userId) {
//        Optional<Team> teamOptional = teamRepository.findById(teamId);
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        if (teamOptional.isPresent() && userOptional.isPresent()) {
//            Team team = teamOptional.get();
//            User user = userOptional.get();
//            team.getUsers().add(user);
//            teamRepository.save(team);
//            System.out.println("i m in iffff");
//        } else {
//             throw new RuntimeException("Team or User not found");
//
//        }
//    }


//    public void addUserToTeam(Long teamId, User user) {
//        Team team = teamRepository.findById(teamId).get();
//        if (team != null) {
//            List<User> members = team.getUsers();
//            members.add(user);
//        }
//    }

    public void addUserToTeam(Long teamId, Long userId) {
        // Récupérer l'équipe et l'utilisateur à partir de leurs identifiants
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (teamOptional != null && userOptional != null) {
//            // Ajouter l'utilisateur à l'équipe
//            teamOptional.get().add(user);
//            // Mettre à jour l'équipe dans la base de données
//            teamRepository.save(team);
        } else {
            throw new IllegalArgumentException("Équipe ou utilisateur introuvable");
        }
    }

    public void removeUserFromTeam(Long teamId, Long userId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (teamOptional.isPresent() && userOptional.isPresent()) {
            Team team = teamOptional.get();
            User user = userOptional.get();
            //team.getUsers().remove(user);
            teamRepository.save(team);
        } else {
            throw new RuntimeException("Team or User not found");
        }
    }



}
