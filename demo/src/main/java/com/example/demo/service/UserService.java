package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.Reponse.UserReponse;
import com.example.demo.DTO.Request.UserRequest;
import com.example.demo.entity.User; 

public interface UserService {
     List<UserReponse> getAllUsers();
     Optional<UserReponse> getUserById(Long id);
     User createUser(UserRequest userRequest) throws Exception;
     User updateUser(Long id, UserRequest userRequest);
     void deleteUser(Long id);
}
