package tn.esprit.pi_backend.service;



import tn.esprit.pi_backend.entities.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(Comment comment, Long userId, Long postId);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByPostId(Long postId);
}
