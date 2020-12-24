package com.app.instazoo.services;

import com.app.instazoo.entity.Comment;
import com.app.instazoo.entity.Post;
import com.app.instazoo.entity.User;
import com.app.instazoo.exceptions.PostNotFoundException;
import com.app.instazoo.repository.CommentRepository;
import com.app.instazoo.repository.PostRepository;
import com.app.instazoo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Post createOrUpdatePost(Post post, Principal principal) {
        User user = getUserByPrincipal(principal);
        post.setUser(user);
        if (post.getId() != null) {
            List<Comment> comments = commentRepository.findAllByPost(post);
            post.setComments(comments);
            return postRepository.save(post);
        }
        LOG.info("Saving Post for User: {}", user.getEmail());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: {}" + user.getEmail()));
    }

    public List<Post> getAllPostForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findAllByUser(user);
    }

    public void deletePost(Long id, Principal principal) {
        Post post = getPostById(id, principal);
        postRepository.delete(post);
    }

    private User getUserByPrincipal(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + userEmail));
    }
}
