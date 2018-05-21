package com.gymi.service;

import com.gymi.model.LoginForm;
import com.gymi.model.Token;
import com.gymi.model.User;
import com.gymi.repository.TokenRepository;
import com.gymi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest


public class AuthServiceTests {

    @Autowired
    private AuthService authService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    UserRepository userRepository;

    LoginForm loginForm;
    User userMock;
    Token tokenMock;
    @Before
    public void initialize() {
        userMock = new User();
        userMock.setId((long) 1);
        userMock.setEmail("test@test.nl");
        userMock.setCreatedDate(new Date());
        userMock.setUpdatedDate(new Date());
        userMock.setFirstName("Test");
        userMock.setLastName("User");
        userMock.setPassword("123");
        userMock.setDateOfBirth(new Date());
        userMock.setUsername("testUser");

        loginForm = new LoginForm();
        loginForm.setUsername("testUser");
        loginForm.setPassword("123");

        tokenMock = new Token();
        tokenMock.setToken("MS41LjIwMTgtMDUtMjEgMTA6MTA6MTAuMA==");
        tokenMock.setValidFrom(new Date());
    }
    
    @Test
    public void testRegisterUserUsernameAlreadyInUse() {
        given(userRepository.findByUsername(userMock.getUsername())).willReturn(new User());
        assertThat(this.authService.registerUser(userMock)).isEqualTo(new ResponseEntity<String>("Username already in use", HttpStatus.CONFLICT));
    }

    @Test
    public void testRegisterUserEmailAlreadyInUse() {
        given(userRepository.findByEmail(userMock.getEmail())).willReturn(new User());
        assertThat(this.authService.registerUser(userMock)).isEqualTo(new ResponseEntity<String>("Email already in use", HttpStatus.CONFLICT));
    }

    @Test
    public void testLoginUserWrongPassword() {
        given(userRepository.findByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword())).willReturn(Optional.ofNullable(null));
        assertThat(authService.login(loginForm)).isEqualTo(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Test
    public void testGenerateToken() {
        Timestamp timestamp = Timestamp.valueOf("2018-05-21 10:10:10.0");
        long randomNumber = 5;
        assertThat(authService.generateToken(userMock, timestamp, randomNumber)).isEqualTo(tokenMock.getToken());
    }
}
