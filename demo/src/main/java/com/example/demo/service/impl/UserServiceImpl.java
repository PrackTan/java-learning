package com.example.demo.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public User createUser(User user) throws Exception
    {
        if(userRepository.findByEmail(user.getEmail()) != null)
        {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }
    public User updateUser(Long id, User user){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
