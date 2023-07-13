package tn.esprit.pi_backend.service;



import tn.esprit.pi_backend.entities.Post;

import java.util.List;

public interface IPostService {
    Post createPost(Post post, Long userId);
    Post getPostById(Long postId);
    // Add additional methods as needed
    List<Post> getPostByUserId(Long UserId);
    List<Post> getAllPosts();
}
