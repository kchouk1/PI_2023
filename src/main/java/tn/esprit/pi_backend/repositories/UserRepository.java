package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi_backend.entities.User_Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_Users, Long> {
    Optional<User_Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User_Users findByEmail(String email);
    List<User_Users> findAll();
}