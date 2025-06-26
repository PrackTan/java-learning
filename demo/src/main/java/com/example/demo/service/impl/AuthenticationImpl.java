package com.example.demo.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.time.temporal.ChronoUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Reponse.AuthenticationReponse;
import com.example.demo.DTO.Reponse.IntrospectReponse;
import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Request.IntrospectRquest;
import com.example.demo.config.JwtConfig;
import com.example.demo.exceptionGlobla.AppExceptions;
import com.example.demo.exceptionGlobla.ErrorCode;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationImpl implements AuthenticationService {
    UserRepository userRepository;
    JwtConfig jwtConfig;
    
    @Override
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new AppExceptions(ErrorCode.NOT_FOUND, "User not found"));
        
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        // TODO: Add password validation logic here
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppExceptions(ErrorCode.INVALID_CREDENTIALS, "Invalid password");
        }
        // For now, just return true if user exists
        return true;
    }
    @Override
    public AuthenticationReponse generateToken(AuthenticationRequest request) {
        if(!authenticate(request)){
            throw new AppExceptions(ErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
        }
        String token = generateToken(request.getEmail());
        return new AuthenticationReponse().builder().token(token).isAuthenticated(true).build();
    }
    private String generateToken(String email){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(email)
        .issuer("self")
        .issueTime(new Date())
        .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
        .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(jwtConfig.getSecretKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jwsObject.serialize();
    }
    @Override
    public IntrospectReponse introspectToken(IntrospectRquest request) {
        var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(jwtConfig.getSecretKey().getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var valid = signedJWT.verify(verifier);
            return new IntrospectReponse().builder().valid(valid && expirationTime.after(new Date())).build();
        } catch (JOSEException e) {
            return new IntrospectReponse().builder().valid(false).build();
        } catch (ParseException e) {
            return new IntrospectReponse().builder().valid(false).build();
        }
    }
}
