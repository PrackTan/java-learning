package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.DTO.Reponse.AuthenticationReponse;
import com.example.demo.DTO.Reponse.IntrospectReponse;
import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Request.IntrospectRquest;

@Service
public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
    AuthenticationReponse generateToken(AuthenticationRequest request);
    IntrospectReponse introspectToken(IntrospectRquest request);
}
