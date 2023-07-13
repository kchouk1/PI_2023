package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.Post;


import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    // Add custom queries or methods if needed

    List<Post> findByUserId(Long userId);
}