package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.ReplyComment;


import java.util.List;

public interface ReplyCommentRepo extends JpaRepository<ReplyComment, Long> {
    // Add custom queries or methods if needed
    List<ReplyComment> findByUserId(Long userId);
}

