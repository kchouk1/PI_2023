package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.Comment;
import tn.esprit.pi_backend.entities.Post;
import tn.esprit.pi_backend.entities.User;


import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    // Add custom queries or methods if needed
    List<Comment> findByUserId(Long userId);
    List<Comment> findCommentByUser(User user);
   // List<Comment> findByPostId(Long postId);
    List<Comment> findCommentByPost(Post post);
}
