package tn.esprit.pi_backend.service;


import tn.esprit.pi_backend.entities.ReactPost;

public interface IReactPostService {
    ReactPost createReactPost(ReactPost reactPost, Long postId, Long userId);
    ReactPost getReactPostById(Long reactPostId);
    String getUsernameOfReactPost(Long reactPostId);
    ReactPost updateReactPost(ReactPost reactPost);
}
