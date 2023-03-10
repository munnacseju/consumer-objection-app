package com.motiur.consumer.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.motiur.consumer.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);
}
