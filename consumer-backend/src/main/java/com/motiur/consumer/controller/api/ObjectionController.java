package com.motiur.consumer.controller.api;

import java.util.HashMap;
import java.util.Optional;

import com.motiur.consumer.constants.ConsumerConstant;
import com.motiur.consumer.model.Objection;
import com.motiur.consumer.model.User;
import com.motiur.consumer.model.UserRole;
import com.motiur.consumer.service.ObjectionService;
import com.motiur.consumer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ObjectionController {
    public static final String GET_OBJECTION = "/objections/{id}";
    public static final String GET_ALL_OBJECTIONS = "/objections";
    public static final String CREATE_OBJECTION = "/objections";
    public static final String DELETE_OBJECTION = "/objections/{id}";

    @Autowired
    private ObjectionService objectionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = GET_ALL_OBJECTIONS, method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> getAllObjections(Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.USER.toString()))) {
            User user = userService.findByEmail(authentication.getName()).get();
            response.put("status", ConsumerConstant.STATUS.OK);
            response.put("message", "Successfully got all objections!");
            response.put("data", objectionService.findByUser(user));
        } else {
            response.put("status", ConsumerConstant.STATUS.NOT_OK);
            response.put("message", "Unauthorized Request!");
        }
        return response;
    }

    @RequestMapping(value = GET_OBJECTION, method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> getObjection(@PathVariable Long id, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.USER.toString()))) {
            User user = userService.findByEmail(authentication.getName()).get();
            Optional<Objection> objection = objectionService.findById(id);
            if (objection.isPresent() && objection.get().getUser().equals(user)) {
                response.put("status", ConsumerConstant.STATUS.OK);
                response.put("message", "Successfully got the objection!");
                response.put("objection", objection.get());
            } else {
                response.put("status", ConsumerConstant.STATUS.NOT_OK);
                response.put("message", "No such objection found!");
            }
        } else {
            response.put("status", ConsumerConstant.STATUS.NOT_OK);
            response.put("message", "Unauthorized Request!");
        }
        return response;
    }

    @RequestMapping(value = CREATE_OBJECTION, method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, Object> addObjection(@RequestBody Objection objection, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.USER.toString()))) {
            User user = userService.findByEmail(authentication.getName()).get();
            objection.setUser(user);
            objectionService.save(objection);
            response.put("status", ConsumerConstant.STATUS.OK);
            response.put("message", "Successfully saved the objection!");
            response.put("data", objectionService.findById(objection.getId()));
        } else {
            response.put("status", ConsumerConstant.STATUS.NOT_OK);
            response.put("message", "Unauthorized Request!");
        }
        return response;
    }

    @RequestMapping(value = DELETE_OBJECTION, method = RequestMethod.DELETE)
    @ResponseBody
    public HashMap<String, Object> deleteObjection(@PathVariable Long id, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.USER.toString()))) {
            User user = userService.findByEmail(authentication.getName()).get();
            Optional<Objection> objection = objectionService.findById(id);
            if (objection.isPresent() && objection.get().getUser().equals(user)) {
                objectionService.deleteById(id);
                response.put("status", ConsumerConstant.STATUS.OK);
                response.put("message", "Successfully deleted the objection!");
            } else {
                response.put("status", ConsumerConstant.STATUS.OK);
                response.put("message", "No such objection found!");
            }
        } else {
            response.put("status", ConsumerConstant.STATUS.NOT_OK);
            response.put("message", "Unauthorized Request!");
        }
        return response;
    }
}
