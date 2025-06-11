package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<String> getTodos() {
        Todo todo = new Todo("title", "description", false);
        this.todoService.handleCreateTodo(todo);
        return ResponseEntity.ok("Hello World todos");
    }
    @GetMapping("/create")
    public ResponseEntity<Todo> createTodo() {
        Todo todo = new Todo("learn java", "learn java by HOIDANIT", false);
        this.todoService.creatTodo(todo);
        return ResponseEntity.ok().body(todo);
    }
    @GetMapping("/all")
    public void getAllTodos() {
      this.todoService.getAllTodos();
    }
    @GetMapping("/{id}")
    public String getTodoById(@PathVariable Long id) {
        this.todoService.getTodoById(id);
        return "Todo found";

    }
    @GetMapping("/title/{title}")
    public String getTodobyTitle(@PathVariable String title) {
        this.todoService.getTodobyTitle(title);
        return "Todo found";
    }
    @GetMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id){
        this.todoService.updateTodo(id);
        return "Todo updated";
    }
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id){
        this.todoService.deleteTodo(id);
        return "Todo Deleted";
    }
}
