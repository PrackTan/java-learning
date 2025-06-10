package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Todo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
public class helloController {
    @GetMapping("/hello")
    public String hello() {
        Todo todo = new Todo("title", "description", false);
        return "Hello World from Spring Boot";
    }
    @PostMapping("")
    public String hello(@RequestBody String name) {
        return "Hello " + name;
    }
}
