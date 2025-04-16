package com.crud.service;

import com.crud.config.JWTUtil;
import com.crud.entity.Users;
import com.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity<?> login(Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Optional<Users>  userOptional =  Optional.empty();
        if(username!=null&&password!=null) {
            userOptional = userRepository.findByUsername(username);
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Username and password must be provided"));
        }
        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        Users user = userOptional.get();
        System.out.println("🔹 Received login request for username: " + username);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            System.out.println("Authentication successful!");
            String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(username));
            System.out.println("Token generated: " + token);

            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }
}
