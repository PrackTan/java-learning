package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;

public interface UserService {
     List<User> getAllUsers();
     Optional<User> getUserById(Long id);
     User createUser(User user) throws Exception;
     User updateUser(Long id, User user);
     void deleteUser(Long id);
}
