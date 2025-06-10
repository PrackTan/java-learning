package com.example.demo.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}
