package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.demo.DTO.Reponse.UserReponse;
import com.example.demo.DTO.Request.UserRequest;
import com.example.demo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
    UserReponse toReponse(User user);
    void updateUser(@MappingTarget User user, UserRequest userRequest);
}
