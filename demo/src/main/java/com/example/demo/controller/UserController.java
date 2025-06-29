package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Reponse.UserReponse;
import com.example.demo.DTO.Request.UserRequest;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<UserReponse>>> getUsers(){
        var authenticate = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authenticate: {}", authenticate.get);
        List<UserReponse> users = userService.getAllUsers();
        var response = new ApiResponse<List<UserReponse>>(HttpStatus.OK, "Users fetched successfully", users, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserReponse>> getUserById(@PathVariable Long id){
        return userService.getUserById(id).map(user -> {
            var response = new ApiResponse<UserReponse>(HttpStatus.OK, "User found successfully", user, null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserRequest userRequest) throws Exception{
        User newUser = userService.createUser(userRequest);
        var response = new ApiResponse<User>(HttpStatus.CREATED, "User created successfully", newUser, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        User updatedUser = userService.updateUser(id, userRequest);
        var response = new ApiResponse<User>(HttpStatus.OK, "User updated successfully", updatedUser, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleException(NoSuchElementException ex){
        var response = new ApiResponse<User>(HttpStatus.NOT_FOUND, "User not found", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        var response = new ApiResponse<User>(HttpStatus.OK, "User deleted successfully", null, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}