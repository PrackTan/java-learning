package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;


@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private final TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping
    public String getAllTodos() {
        Todo todo = new Todo("title", "description", false);
        this.todoService.handleCreateTodo(todo);
        return "Hello World todos";
    }
}
