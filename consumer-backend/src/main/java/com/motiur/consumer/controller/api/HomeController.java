package com.motiur.consumer.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Welcome to consumer API!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public HashMap<String, String> test() {
        HashMap<String, String> values = new HashMap<>();       
        values.put("hello", "Welcome to consumer testing API!");
        return values;
    }
}
