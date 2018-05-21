package com.gymi.service;

import com.gymi.model.User;
import com.gymi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    User userMock;

    @Before
    public void initialize() {
        User userMock = new User();
        userMock.setId((long) 5);
        userMock.setEmail("test@test.nl");
        userMock.setCreatedDate(new Date());
        userMock.setUpdatedDate(new Date());
        userMock.setFirstName("Test");
        userMock.setLastName("User");
        userMock.setPassword("123");
        userMock.setDateOfBirth(new Date());
        userMock.setUsername("testUser");

    }

    @Test
    public void testGetUserById() {
        long number = 5;

        given(this.userRepository.findById(number)).willReturn(Optional.ofNullable(userMock));
        assertThat(this.userService.getUserById((number))).isEqualTo(userMock);

    }

    @Test
    public void testGetUserByIdWhenUserNotExist() {
        long number = -1;

        Optional<User> empty = Optional.empty();
        given(this.userRepository.findById(number)).willReturn(empty);

        assertThat(this.userService.getUserById(number)).isEqualTo(null);
    }

    @Test
    public void testGetUserByUsername() {
        String username = "testuser";

        given(this.userRepository.findByUsername(username)).willReturn(userMock);
        assertThat(this.userService.getUserByUsername(username)).isEqualTo(userMock);
    }

    @Test
    public void testGetUserByUsernameWhenNotExist() {
        String username = "testuser";

        given(this.userRepository.findByUsername(username)).willReturn(null);
        assertThat(this.userService.getUserByUsername(username)).isEqualTo(null);
    }
}
