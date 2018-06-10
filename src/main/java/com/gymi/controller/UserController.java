package com.gymi.controller;

import com.gymi.model.Friend;
import com.gymi.model.FriendResponse;
import com.gymi.model.Session;
import com.gymi.model.User;
import com.gymi.service.AuthService;
import com.gymi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@RequestHeader("Authorization") String authToken, @PathVariable("id") long id) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        else {
            User user = userService.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity getUserByUsername(@RequestHeader("Authorization") String authToken, @PathVariable("username") String username) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        else {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentUser(@RequestHeader("Authorization") String authToken) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        else return new ResponseEntity<>(authService.isAuthenticated(authToken), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity getFriendsForUser(@RequestHeader("Authorization") String authToken, @PathVariable("id") long userId) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        else {
            User user = userService.getUserById(userId);
            if (user != null) {
                List<FriendResponse> friendList = userService.getFriendsForUser(user.getId());
                return new ResponseEntity<>(friendList, HttpStatus.OK);
            } else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sendFriendRequest/{userId1}/{userId2}")
    public ResponseEntity sendFriendRequest(@RequestHeader("Authorization") String authToken,
                                            @PathVariable("userId1") int userId1,
                                            @PathVariable("userId2") int userId2) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        User user1 = userService.getUserById(userId1);
        User user2 = userService.getUserById(userId2);
        if (user1 != null && user2 != null) {
            Friend friend = userService.saveFriend(user1.getId(), user2.getId());
            if (friend == null) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(friend, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteFriendship/{userId2}")
    public ResponseEntity deleteFriendship(@RequestHeader("Authorization") String authToken,
                                           @PathVariable("userId2") int userId2
    ) {
        if (authService.isAuthenticated(authToken) == null) return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        Long userId1 = authService.isAuthenticated(authToken).getId();

        try {
            userService.deleteFriendship(userId1, (long) userId2);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
}
