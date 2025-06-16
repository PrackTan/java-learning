package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
   
    @Test
    public void testCreateUser() throws Exception {
        // Arrange: chuẩn bị
        User inputUser = new User(null, "test", "test@test.com", "test");
        User outputUser = new User(1L, "test", "test@test.com", "test");
        
        // Act: thực hiện
        when(userRepository.findByEmail(inputUser.getEmail())).thenReturn(null);
        when(userRepository.save(inputUser)).thenReturn(outputUser);
        User result = userService.createUser(inputUser);
        
        // Assert: kiểm tra
        assertEquals(outputUser, result);
    }
}
