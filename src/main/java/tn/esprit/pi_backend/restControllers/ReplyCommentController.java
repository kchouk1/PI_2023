package tn.esprit.pi_backend.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.ReplyComment;
import tn.esprit.pi_backend.service.IReplyCommentService;

import java.util.List;
@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/reply-comments")
public class ReplyCommentController {

    private final IReplyCommentService replyCommentService;

    @Autowired
    public ReplyCommentController(IReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping("/{userId}/{commentId}")
    public ResponseEntity<ReplyComment> createReplyComment(@RequestBody ReplyComment replyComment, @PathVariable Long userId, Long commentId) {
        ReplyComment createdReplyComment = replyCommentService.createReplyComment(replyComment, userId,commentId);
        return new ResponseEntity<>(createdReplyComment, HttpStatus.CREATED);
    }

    @GetMapping("/{replyCommentId}")
    public ResponseEntity<ReplyComment> getReplyCommentById(@PathVariable Long replyCommentId) {
        ReplyComment replyComment = replyCommentService.getReplyCommentById(replyCommentId);
        return new ResponseEntity<>(replyComment, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReplyComment>> getReplyCommentsByUserId(@PathVariable Long userId) {
        List<ReplyComment> replyComments = replyCommentService.getReplyCommentsByUserId(userId);
        return new ResponseEntity<>(replyComments, HttpStatus.OK);
    }

    // Add additional endpoints as needed
}
