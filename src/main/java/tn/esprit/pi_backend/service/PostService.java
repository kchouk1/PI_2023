package tn.esprit.pi_backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Post;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.PostRepo;
import tn.esprit.pi_backend.repositories.UserRepository;


import java.util.List;

@Service
public class PostService implements  IPostService{




        private final PostRepo postRepository;
        private final UserService userService;
        private  final UserRepository userRepository;

        @Autowired
        public PostService(PostRepo postRepository, UserService userService, UserRepository userRepository) {
            this.postRepository = postRepository;
            this.userService = userService;
            this.userRepository = userRepository;
        }

        @Override
        public Post createPost(Post post, Long userId) {
            User user = userRepository.getUserById(userId);
            post.setUser(user);

            // Add any validation or business logic here
            return postRepository.save(post);
        }

        @Override
        public Post getPostById(Long postId) {
            return postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
        }

    @Override
    public List<Post> getPostByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> getAllPosts() {
        return  postRepository.findAll();
    }


    // Implement additional methods as needed


}
