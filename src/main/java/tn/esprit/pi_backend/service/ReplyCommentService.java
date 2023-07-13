package tn.esprit.pi_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Comment;
import tn.esprit.pi_backend.entities.ReplyComment;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.ReplyCommentRepo;
import tn.esprit.pi_backend.repositories.UserRepository;


import java.util.List;
@Service
public class ReplyCommentService implements IReplyCommentService{

        private final ReplyCommentRepo replyCommentRepository;
        private final UserService userService;

        private  final CommentService commentService;
        private final UserRepository userRepository;

        @Autowired
        public ReplyCommentService(ReplyCommentRepo replyCommentRepository, UserService userService, CommentService commentService, UserRepository userRepository) {
            this.replyCommentRepository = replyCommentRepository;
            this.userService = userService;
            this.commentService=commentService;
            this.userRepository = userRepository;
        }

        @Override
        public ReplyComment createReplyComment(ReplyComment replyComment, Long userId , Long commentId) {
            Comment comment = commentService.getCommentById(commentId);
            User user = userRepository.getUserById(userId);
            replyComment.setComment(comment);
            replyComment.setUser(user);

            // Add any validation or business logic here
            return replyCommentRepository.save(replyComment);
        }

        @Override
        public ReplyComment getReplyCommentById(Long replyCommentId) {
            return replyCommentRepository.findById(replyCommentId)
                    .orElseThrow(() -> new RuntimeException("ReplyComment not found"));
        }

        @Override
        public List<ReplyComment> getReplyCommentsByUserId(Long userId) {
            return replyCommentRepository.findByUserId(userId);
        }

        // Implement additional methods as needed


}
