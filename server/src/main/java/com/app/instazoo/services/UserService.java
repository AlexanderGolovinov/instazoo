package com.app.instazoo.services;

import com.app.instazoo.entity.User;
import com.app.instazoo.exceptions.UserExistException;
import com.app.instazoo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrUpdateUser(User userIn) {
        User user = new User();
        LOG.info("Saving new User {}", user.getEmail());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

}
