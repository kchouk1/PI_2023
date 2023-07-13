package tn.esprit.pi_backend.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pi_backend.entities.Team;
import tn.esprit.pi_backend.entities.User;


import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

Optional<Team> findByUsers(User user);

}
