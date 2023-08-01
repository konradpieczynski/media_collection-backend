package com.media_collection.backend.service;


import com.media_collection.backend.controller.exceptions.UserNotFoundException;
import com.media_collection.backend.domain.User;
import com.media_collection.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public void deleteById(Long userId) throws UserNotFoundException {
        User toBeDeletedUser = repository.findById(userId).orElseThrow(UserNotFoundException::new);
        repository.delete(toBeDeletedUser);
    }

    public List<User> findUserByUserName(String name) {
        return repository.findUsersByUserName(name);
    }
}