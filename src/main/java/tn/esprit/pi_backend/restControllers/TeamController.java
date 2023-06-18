package tn.esprit.pi_backend.restControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.esprit.pi_backend.entities.Team;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.TeamRepository;

@CrossOrigin(maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
	@Autowired
	TeamRepository teamRepository;

	@GetMapping()
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@PostMapping("/{teamId}")
	public Team addUserToTeam(@PathVariable Long teamId, @RequestBody User user) {
		Optional<Team> teamOpt = teamRepository.findById(teamId);
		Team team = teamOpt.get();
		List<User> users = team.getUsers();
		users.add(user);
		team.setUsers(users);
		return teamRepository.save(team);
	}
}
