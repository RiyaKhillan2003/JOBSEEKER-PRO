package com.jobseekerpro.service;

import com.jobseekerpro.entity.AppUser;
import com.jobseekerpro.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSignup(){
        AppUser user = userService.signup("test@example.com","pass123","Test User","JOB_SEEKER");
        assertNotNull(user);
        assertEquals("test@example.com",user.getEmail());
        assertEquals("JOB_SEEKER",user.getRole());
    }

    @Test
    public void testLoginSuccess(){
        userService.signup("login@example.com","pass123","Login User","JOB_SEEKER");
        AppUser user = userService.login("login@example.com","pass123");
        assertNotNull(user);
        assertEquals("login@example.com",user.getEmail());
    }

    @Test
    public void testLoginWrongEmail(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login("wrong@example.com","pass123");

        });
        assertEquals("User with email wrong@example.com not found", exception.getMessage());
    }
}
