package com.crud.service;

import com.crud.DTO.UserDTO;
import com.crud.entity.Users;
import com.crud.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crud.Exception.UserNotFoundException;
import com.crud.Utility.PasswordUtility;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTO userDTO;
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }

    public Users createUser(Users user) {
        log.info("Creating user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public Users updateUser(Long id, @Valid UserDTO userDetails) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> {
                    updateEntityFromDTO(user,userDetails);
                    return userRepository.save(user);
                }).orElseThrow(() -> {
            log.error("User with ID {} not found", id);
            return new UserNotFoundException("User not found"+" "+id);
        });
    }
    public void updateEntityFromDTO(Users user, UserDTO dto) {
        user.setUsername(dto.getUsername());
        user.setPassword(PasswordUtility.encode(userDTO.getPassword()));
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

