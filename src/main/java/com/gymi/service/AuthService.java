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

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    public ResponseEntity registerUser(@Valid User user) {
        if(!isUsernameUnique(user.getUsername())) return new ResponseEntity<String>("Username already in use", HttpStatus.CONFLICT);
        if(!isEmailUnique(user.getEmail())) return new ResponseEntity<String>("Email already in use", HttpStatus.CONFLICT);
        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity login(LoginForm loginForm) {
        try {
            User user = userRepository.findByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword());
            String token = generateToken(user);
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String generateToken(User user) {
        Random random = new Random();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        builder.append(user.getId()).append(".").append(random.nextLong()).append(".").append(timestamp);
        String token = Base64.getEncoder().encodeToString(builder.toString().getBytes());

        Token tokenObject = new Token();
        tokenObject.setToken(token);
        tokenObject.setValidFrom(timestamp);
        saveTokenToDatabase(tokenObject);
        return token;
    }

    public ResponseEntity isAuthenticated(String authToken) {
        Token token = tokenRepository.findByToken(authToken);
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        byte[] decryptToken = Base64.getDecoder().decode(token.getToken());
        String decryptTokenStr = null;
        try {
            decryptTokenStr = new String(decryptToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String[] information = decryptTokenStr.split("\\.");
        Optional<User> user = userRepository.findById(Long.valueOf(information[0]));
        try {
            if (user.get() != null && isTokenValid(information[2])) {
                return null;
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private void saveTokenToDatabase(Token token) {
        tokenRepository.save(token);
    }

    public boolean isTokenValid(String timeCreated) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date tokenDate;
        Date todayDate;
        try {
            tokenDate = format.parse(timeCreated);
            todayDate = new Date();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        long difference = todayDate.getTime() - tokenDate.getTime();
        return difference < MILLIS_PER_DAY;
    }

    private boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username) == null;
    }

    private boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }
}
