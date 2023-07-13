package tn.esprit.pi_backend.service;



import tn.esprit.pi_backend.entities.ReplyComment;

import java.util.List;

public interface IReplyCommentService {
    ReplyComment createReplyComment(ReplyComment replyCommentService, Long userId, Long commentId);
    ReplyComment getReplyCommentById(Long replyCommentId);
    List<ReplyComment> getReplyCommentsByUserId(Long userId);
}
