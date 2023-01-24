package com.motiur.consumer.service;

import java.util.Optional;

import com.motiur.consumer.model.User;

public interface UserService {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);
}
