package com.api.bigu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping("/user")
    public ResponseEntity<String> hello() {

        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {

        return ResponseEntity.ok("Hello ADMIN!");
    }
}
