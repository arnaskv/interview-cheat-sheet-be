package com.interview.manager.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/echo")
public class EchoController {
    
    @GetMapping
    public String echo() {
        return "Hello, World!";
    }
}
