package com.media_collection.backend.service;

import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void saveUser() {
        //Given
        User user = User.builder()
                .userName("Test user")
                .build();
        //When
        User savedUser = userService.saveUser(user);
        //Then
        assertEquals(user.getUserName(),savedUser.getUserName());
        assertEquals(1,userService.findUserByUserName("Test user").size());
        userRepository.deleteAll();
    }

    @Test
    void findUserById() {
        //Given
        User user = User.builder()
                .userId(1L)
                .userName("Test user")
                .build();
        //When
        User savedUser = userService.saveUser(user);
        //Then
        try {
            assertEquals(savedUser.getUserId(), userService.findUserById(savedUser.getUserId()).getUserId());
        } catch (UserNotFoundException e) {
            fail();
        }
        userRepository.deleteAll();
    }

    @Test
    void getUsers() {
        //Given
        User user = User.builder()
                .userName("Test user")
                .build();
        User user2 = User.builder()
                .userName("Test user2")
                .build();
        //When
        userService.saveUser(user);
        userService.saveUser(user2);
        //Then
        assertEquals(2,userService.getUsers().size());
        userRepository.deleteAll();
    }

    @Test
    void deleteById() {
        //Given
        User user = User.builder()
                .userName("Test user")
                .userId(1L)
                .build();
        User user2 = User.builder()
                .userName("Test user2")
                .userId(2L)
                .build();
        //When
        userService.saveUser(user);
        User savedUser = userService.saveUser(user2);
        try {
            userService.deleteById(savedUser.getUserId());
        } catch (UserNotFoundException e) {
            fail();
        }
        //Then
        assertEquals(1,userRepository.count());
        userRepository.deleteAll();
    }

    @Test
    void findUserByUserName() {
        //Given
        User user = User.builder()
                .userName("Test user")
                .userId(1L)
                .build();
        User user2 = User.builder()
                .userName("Test user2")
                .userId(2L)
                .build();
        //When
        userService.saveUser(user);
        userService.saveUser(user2);
        List<User> foundUsers = userService.findUserByUserName("Test user2");

        //Then
        assertEquals("Test user2", foundUsers.get(0).getUserName());
        userRepository.deleteAll();
    }
}