package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.example.demo.DTO.Request.UserRequest;
import com.example.demo.DTO.Reponse.UserReponse;
import com.example.demo.entity.User;
import com.example.demo.exceptionGlobla.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.exceptionGlobla.AppExceptions;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
     UserRepository userRepository;
     UserMapper userMapper;
    /// get all users
    public List<UserReponse> getAllUsers(){
        return userRepository.findAll().stream()
            .map(userMapper::toReponse)
            .collect(Collectors.toList());
    }
    /// get user by id
    public Optional<UserReponse> getUserById(Long id){
        return userRepository.findById(id).map(userMapper::toReponse);
    }
    /// create user
    public User createUser(UserRequest userRequest) throws Exception
    {
        if(userRepository.findByEmail(userRequest.getEmail()) != null)
        {
            throw new AppExceptions(ErrorCode.BAD_REQUEST, "Email already exists");
        }
        // convert UserRequest to User entity
        User newUser = userMapper.toEntity(userRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }
    /// update user
    public User updateUser(Long id, UserRequest userRequest){
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new AppExceptions(ErrorCode.NOT_FOUND, "User not found"));
        
        // Check if email is being changed and if it already exists
        if (!existingUser.getEmail().equals(userRequest.getEmail()) && 
            userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new AppExceptions(ErrorCode.BAD_REQUEST, "Email already exists");
        }
        
        // Update existing user with new data
        userMapper.updateUser(existingUser, userRequest);
        return userRepository.save(existingUser);
    }
    /// delete user
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new AppExceptions(ErrorCode.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}
