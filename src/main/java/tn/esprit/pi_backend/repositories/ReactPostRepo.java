package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.ReactPost;


public interface ReactPostRepo extends JpaRepository<ReactPost, Long> {
    // Add custom queries or methods if needed
}