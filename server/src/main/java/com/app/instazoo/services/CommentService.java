package com.app.instazoo.services;

import com.app.instazoo.DTO.CommentDTO;
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

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: {}" + user.getEmail()));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        comment.setUsername(user.getUsername());
        comment.setPost(post);
        LOG.info("Saving comment for Post: {}", post.getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));
        List<Comment> comments = commentRepository.findAllByPost(post);

        return comments;
    }

    public void deleteComment(Long commentId, Principal principal) {
        User user = getUserByPrincipal(principal);
        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId());

        commentRepository.delete(comment);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
    }

    private Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: {}" + user.getEmail()));
    }
}
