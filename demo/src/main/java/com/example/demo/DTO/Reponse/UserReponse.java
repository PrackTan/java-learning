package com.example.demo.DTO.Reponse;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Set;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReponse {
    String name;
    String email;
    Set<String> roles;
}
