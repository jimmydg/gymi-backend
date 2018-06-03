package com.gymi.service;

import com.gymi.model.Friend;
import com.gymi.model.User;
import com.gymi.repository.FriendRepository;
import com.gymi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Autowired
    FriendRepository friendRepository;

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

    public Friend saveFriend(long id1, long id2)
    {
        Friend friend = new Friend();

        if (userRepository.findById(id2).isPresent())
        {
            friend.setUserId1(id1);
            friend.setUserId2(id2);
            friend.setHasAccepted(false);
            try {
                return friendRepository.save(friend);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public List<Friend> getFriendsForUser(long userId) {
        return friendRepository.findFriendsforUser(userId);
    }
}
