package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Todo;
import com.example.demo.reponsitory.TodoRepository;


@Service
public class TodoServiceImpl {
    @Autowired
    private final TodoRepository todoRepository;
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    public void handleCreateTodo(Todo todo) {
        System.out.println("Todo created: " + todo.toString());
        todoRepository.save(todo);
    }
    public String creatTodo(Todo todo){
        todoRepository.save(todo);
        return "Todo created successfully";
    }
    public void getAllTodos() {
       List<Todo> todos = todoRepository.findAll();
       todos.forEach(System.out::println);
    }
    public void getTodoById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            System.out.println(todo.get());
        } else {
            System.out.println("Todo not found");
        }
    }
    public void getTodobyTitle(String title) {
        Optional<Todo> todo = todoRepository.findByTitle(title);
        if (todo.isPresent()) {
            System.out.println(todo.get());
        } else {
            System.out.println("Todo not found");
        }
    }
    public void updateTodo(Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            Todo currentTodo = todo.get();
            currentTodo.setCompleted(true);
            this.todoRepository.save(currentTodo);
        }
    }
    public void deleteTodo(Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            todoRepository.deleteById(id);
        }
    }   
}
    