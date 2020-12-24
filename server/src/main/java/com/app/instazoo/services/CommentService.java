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

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(Comment.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long postId, Comment comment, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = getPostById(postId, principal);

        comment.setUsername(user.getUsername());
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    private User getUserByPrincipal(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + userEmail));
    }

    private Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: {}" + user.getEmail()));
    }
}
