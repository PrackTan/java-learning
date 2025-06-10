package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Todo;
import com.example.demo.reponsitory.TodoRepository;


@Service
public class TodoService {
    @Autowired
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    public void handleCreateTodo(Todo todo) {
        System.out.println("Todo created: " + todo.toString());
        todoRepository.save(todo);
    }
}
        