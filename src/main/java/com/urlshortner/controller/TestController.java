package com.urlshortner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/Hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
