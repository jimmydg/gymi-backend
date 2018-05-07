package com.gymi.controller;

import com.gymi.model.LoginForm;
import com.gymi.model.User;
import com.gymi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    AuthenticationController() {

    }
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        return authService.login(loginForm);
    }
}
