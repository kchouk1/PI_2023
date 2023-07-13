package tn.esprit.pi_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Comment;
import tn.esprit.pi_backend.entities.Post;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.CommentRepo;
import tn.esprit.pi_backend.repositories.UserRepository;


import java.util.List;
@Service
public class CommentService implements ICommentService{





        private final CommentRepo commentRepository;
        private final UserService userService;
        private final  IPostService postService;
        private final UserRepository userRepository;

        @Autowired
        public CommentService(CommentRepo commentRepository, UserService userService, PostService postService, UserRepository userRepository) {
            this.commentRepository = commentRepository;
            this.userService = userService;
            this.postService = postService;
            this.userRepository = userRepository;
        }

        @Override
        public Comment createComment(Comment comment, Long userId, Long postId) {
            Post post = postService.getPostById(postId);
            User user = userRepository.getUserById(userId);
            comment.setPost(post);
            comment.setUser(user);

            // Add any validation or business logic here
            return commentRepository.save(comment);
        }

        @Override
        public Comment getCommentById(Long commentId) {
            return commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
        }

        @Override
        public List<Comment> getCommentsByUserId(Long userId) {
            User user = userRepository.getUserById(userId);
            return commentRepository.findCommentByUser(user);
        }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
            Post post=postService.getPostById(postId);
        return commentRepository.findCommentByPost(post);
    }

    // Implement additional methods as needed


}
