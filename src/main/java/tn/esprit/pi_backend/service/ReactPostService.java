package tn.esprit.pi_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Post;
import tn.esprit.pi_backend.entities.ReactPost;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.ReactPostRepo;
import tn.esprit.pi_backend.repositories.UserRepository;


@Service
public class ReactPostService implements IReactPostService{


        private final ReactPostRepo reactPostRepository;
        private final UserService userService;
        private final PostService postService;
        private final UserRepository userRepository;

        @Autowired
        public ReactPostService(ReactPostRepo reactPostRepository, UserService userService, PostService postService, UserRepository userRepository) {
            this.reactPostRepository = reactPostRepository;
            this.userService = userService;
            this.postService = postService;
            this.userRepository = userRepository;
        }

        @Override
        public ReactPost createReactPost(ReactPost reactPost, Long postId, Long userId) {
            Post post = postService.getPostById(postId);
            User user = userRepository.getUserById(userId);
            reactPost.setPost(post);
            reactPost.setUser(user);

            // Add any validation or business logic here
            return reactPostRepository.save(reactPost);
        }

        @Override
        public ReactPost getReactPostById(Long reactPostId) {
            return reactPostRepository.findById(reactPostId)
                    .orElseThrow(() -> new RuntimeException("ReactPost not found"));
        }

        @Override
        public String getUsernameOfReactPost(Long reactPostId) {
            ReactPost reactPost = reactPostRepository.findById(reactPostId)
                    .orElseThrow(() -> new RuntimeException("ReactPost not found"));
            User user = reactPost.getUser();
            return user.getUsername();
        }
    @Override
    public ReactPost updateReactPost(ReactPost reactPost) {
        return reactPostRepository.save(reactPost);
    }

        // Implement additional methods as needed


}
