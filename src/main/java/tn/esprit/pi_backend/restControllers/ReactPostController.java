package tn.esprit.pi_backend.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Post;
import tn.esprit.pi_backend.entities.ReactPost;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.ReactPostRepo;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.service.IPostService;
import tn.esprit.pi_backend.service.IReactPostService;

import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/react-posts")
public class ReactPostController {

    private final IReactPostService reactPostService;
    private final IPostService postService;
    private final UserRepository userRepository;
    private final ReactPostRepo reactPostRepo;

    @Autowired
    public ReactPostController(IReactPostService reactPostService, IPostService postService, UserRepository userRepository, ReactPostRepo reactPostRepo) {
        this.reactPostService = reactPostService;
        this.postService = postService;
        this.userRepository = userRepository;
        this.reactPostRepo = reactPostRepo;
    }

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<ReactPost> createReactPost(@RequestBody ReactPost reactPost, @PathVariable Long postId, @PathVariable Long userId) {
        ReactPost createdReactPost = reactPostService.createReactPost(reactPost, postId, userId);
        return new ResponseEntity<>(createdReactPost, HttpStatus.CREATED);
    }

    @GetMapping("/{reactPostId}")
    public ResponseEntity<ReactPost> getReactPostById(@PathVariable Long reactPostId) {
        ReactPost reactPost = reactPostService.getReactPostById(reactPostId);
        return new ResponseEntity<>(reactPost, HttpStatus.OK);
    }

    @GetMapping("/{reactPostId}/username")
    public ResponseEntity<String> getUsernameOfReactPost(@PathVariable Long reactPostId) {
        String username = reactPostService.getUsernameOfReactPost(reactPostId);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }
    @GetMapping("/like/{id}/{postId}/{userId}")
    public int likePost(@PathVariable Long id,
                        @PathVariable Long postId,
                        @PathVariable Long userId) {
        ReactPost existingReactPost = reactPostService.getReactPostById(id);
        Post post = postService.getPostById(postId);
        User user = userRepository.getUserById(userId);

        existingReactPost.setPost(post);
        existingReactPost.setUser(user);
        existingReactPost.setLikes(existingReactPost.getLikes() + 1);
        if (existingReactPost.getDislikes() != 0) {
            existingReactPost.setDislikes(existingReactPost.getDislikes() - 1);

        } else {
            existingReactPost.setDislikes(existingReactPost.getDislikes());
        }
        reactPostService.updateReactPost(existingReactPost);

        return existingReactPost.getLikes();
    }
    @GetMapping("/dislike/{id}/{postId}/{userId}")
    public int dislikePost(@PathVariable Long id,
                           @PathVariable Long postId,
                           @PathVariable Long userId) {
        ReactPost existingReactPost = reactPostService.getReactPostById(id);
        Post post = postService.getPostById(postId);
        User user = userRepository.getUserById(userId);


        existingReactPost.setPost(post);
        existingReactPost.setUser(user);
        existingReactPost.setDislikes(existingReactPost.getDislikes() + 1);
        if (existingReactPost.getLikes() != 0) {
            existingReactPost.setLikes(existingReactPost.getLikes() - 1);
        } else {
            existingReactPost.setLikes(existingReactPost.getLikes());
        }
        reactPostService.updateReactPost(existingReactPost);

        return existingReactPost.getDislikes();
    }
    @GetMapping()
    public List<ReactPost> reactPosts(){
        return reactPostRepo.findAll();
    }



    // Add additional endpoints as needed
}
