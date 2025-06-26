package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Reponse.AuthenticationReponse;
import com.example.demo.DTO.Reponse.IntrospectReponse;
import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Request.IntrospectRquest;
import com.example.demo.entity.ApiResponse;
import com.example.demo.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationReponse>> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.generateToken(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Login successful", result, null));

    }
    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectReponse>> introspect(@RequestBody IntrospectRquest request) {
        var result = authenticationService.introspectToken(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Introspect successful", result, null));
    }
}
