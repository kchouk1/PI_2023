package tn.esprit.pi_backend.repositories;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi_backend.entities.Role;
import tn.esprit.pi_backend.entities.Team;
import tn.esprit.pi_backend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findAll();
    Optional<User> findById(Id id);

    User getUserById(Long id);
    long count();

    int countByRoles(Role role);


}