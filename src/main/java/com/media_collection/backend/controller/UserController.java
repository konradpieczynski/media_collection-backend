package com.media_collection.backend.controller;

import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.UserDto;
import com.media_collection.backend.mapper.UserMapper;
import com.media_collection.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.findUserById(userId)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userMapper.mapToUserDtoList(userService.getUsers()));
    }

    @PostMapping(value ="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
        return ResponseEntity.ok("New user " + userDto.getUserName() + " was successfully created");
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        String userName = userService.findUserById(userId).getUserName();
        userService.deleteById(userId);
        return ResponseEntity.ok("User " + userName + " was successfully deleted");
    }
}
