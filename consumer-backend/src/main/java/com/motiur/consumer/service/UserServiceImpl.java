package com.motiur.consumer.service;

import java.util.Optional;

import com.motiur.consumer.model.User;
import com.motiur.consumer.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // @Override
    // public Optional<User> findByUsername(String username) {
    //     return userRepository.findByUsername(username);
    // }
}
