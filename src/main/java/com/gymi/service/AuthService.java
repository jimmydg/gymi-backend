package com.gymi.service;

import com.gymi.model.LoginForm;
import com.gymi.model.Token;
import com.gymi.model.User;
import com.gymi.repository.TokenRepository;
import com.gymi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    public ResponseEntity registerUser(@Valid User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity login(LoginForm loginForm) {
        try {
            User user = userRepository.findByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword());
            Random random = new Random();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            StringBuilder builder = new StringBuilder();
            builder.append(user.getId()).append(".").append(random.nextLong()).append(".").append(timestamp);
            String token = Base64.getEncoder().encodeToString(builder.toString().getBytes());

            Token tokenObject = new Token();
            tokenObject.setToken(token);
            tokenObject.setValidTill(timestamp);
            saveTokenToDatabase(tokenObject);

            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private void saveTokenToDatabase(Token token) {
        tokenRepository.save(token);
    }


}
