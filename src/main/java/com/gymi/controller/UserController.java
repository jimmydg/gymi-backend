package com.gymi.controller;

import com.gymi.service.AuthService;
import com.gymi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    UserController() {

    }

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@RequestHeader("MJWT-auth") String authToken, @PathVariable("id") long id) {
        if (authService.isAuthenticated(authToken) != null) return authService.isAuthenticated(authToken);
        else return userService.getUserById(id);
    }
}
