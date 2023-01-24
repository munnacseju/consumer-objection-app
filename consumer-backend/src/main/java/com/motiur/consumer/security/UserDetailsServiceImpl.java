package com.motiur.consumer.security;

import java.util.Collections;
import java.util.Optional;

import com.motiur.consumer.model.User;
import com.motiur.consumer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
