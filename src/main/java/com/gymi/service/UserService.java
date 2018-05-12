package com.gymi.service;

import com.gymi.exception.ResourceNotFoundException;
import com.gymi.model.User;
import com.gymi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    public User getUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }
        else {
            return null;
        }
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
