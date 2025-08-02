package com.lcwd.electronic.store.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userId("U123")
                .name("Badal Patil")
                .email("badal@example.com")
                .password("secret123")
                .gender("Male")
                .about("Java Backend Developer")
                .imageName("profile.png")
                .build();
    }

    @Test
    void testUserGetters() {
        assertEquals("U123", user.getUserId());
        assertEquals("Badal Patil", user.getName());
        assertEquals("badal@example.com", user.getEmail());
        assertEquals("secret123", user.getPassword());
        assertEquals("Male", user.getGender());
        assertEquals("Java Backend Developer", user.getAbout());
        assertEquals("profile.png", user.getImageName());
    }

    @Test
    void testUserSetters() {
        user.setUserId("U456");
        user.setName("Ganpat Sathe");
        user.setEmail("ganpat@example.com");
        user.setPassword("newpass");
        user.setGender("Other");
        user.setAbout("Spring Boot Expert");
        user.setImageName("dp.png");

        assertEquals("U456", user.getUserId());
        assertEquals("Ganpat Sathe", user.getName());
        assertEquals("ganpat@example.com", user.getEmail());
        assertEquals("newpass", user.getPassword());
        assertEquals("Other", user.getGender());
        assertEquals("Spring Boot Expert", user.getAbout());
        assertEquals("dp.png", user.getImageName());
    }

    @Test
    void testToString() {
        String output = user.toString();
        assertTrue(output.contains("Badal Patil"));
        assertTrue(output.contains("badal@example.com"));
    }
}
