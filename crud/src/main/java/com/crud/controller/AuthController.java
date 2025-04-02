package com.crud.controller;



import com.crud.config.JWTUtil;
import com.crud.entity.Users;
import com.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        Optional<Users> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            System.out.println("User found: " + user.getUserName());
        } else {
            System.out.println("User not found");
        }
        System.out.println("🔹 Received login request for username: " + username);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("Authentication successful!");

            String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(username));
            System.out.println("Token generated: " + token);

            return Map.of("token", token);
        } catch (Exception e) {
            System.out.println(" Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }

}

