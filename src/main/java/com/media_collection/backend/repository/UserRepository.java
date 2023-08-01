package com.media_collection.backend.repository;

import com.media_collection.backend.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User save(User User);

    @Override
    Optional<User> findById(Long UserId);

    @Override
    List<User> findAll();

    @Override
    void deleteById(Long UserId);

    List<User> findUsersByUserName(String name);
}