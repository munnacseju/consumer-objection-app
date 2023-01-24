package com.motiur.consumer.controller.api;

import java.util.HashMap;
import java.util.Optional;

import com.motiur.consumer.constants.ConsumerConstant;
import com.motiur.consumer.model.User;
import com.motiur.consumer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    public static final String REGISTER = "/register";

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = REGISTER, method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> registerUser(@RequestBody User user) {
        HashMap<String, Object> response = new HashMap<>();
        Optional<User> existsUser = userService.findByEmail(user.getEmail());
        if (existsUser.isPresent()) {
            response.put("status", "EMAIL_EXISTS");
            response.put("error", "User is already registered!");
            return response;
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userService.save(user);
        response.put("status", ConsumerConstant.STATUS.OK);
        response.put("message", "Successfully registered!");
        return response;
    }
}
