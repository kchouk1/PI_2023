package tn.esprit.pi_backend.restControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.esprit.pi_backend.entities.Team;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.TeamRepository;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.service.TeamService;

@CrossOrigin(maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
	@Autowired
	private TeamRepository teamRepository;
	private UserRepository userRepository;
	private final TeamService teamService;

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
	@GetMapping("/{id}")
	public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
		Optional<Team> teamOptional = teamService.getTeamById(id);

		return teamOptional.map(team -> new ResponseEntity<>(team, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Team> createTeam(@RequestBody Team team) {
		Team createdTeam = teamService.createTeam(team);
		return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody Team team) {
		Team updatedTeam = teamService.updateTeam(id, team);
		return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
		teamService.deleteTeam(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}



/*

package tn.esprit.pi_backend.restControllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.pi_backend.entities.Team;

import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.TeamRepository;
import tn.esprit.pi_backend.repositories.UserRepository;

import tn.esprit.pi_backend.service.TeamService;

import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
        Optional<Team> teamOptional = teamService.getTeamById(id);

        return teamOptional.map(team -> new ResponseEntity<>(team, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{teamId}/users")
    public ResponseEntity<String> addUserToTeam(@PathVariable("teamId") Long teamId, @RequestBody User user) {
        teamService.addUserToTeam(teamId, user);
        return ResponseEntity.ok("Utilisateur ajouté à l'équipe avec succès");
    }

    @DeleteMapping("/{teamId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromTeam(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) {
        teamService.removeUserFromTeam(teamId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

 */