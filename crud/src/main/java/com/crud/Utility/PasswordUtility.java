package com.crud.Utility;

import com.crud.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordUtility {

    @Autowired
    private UserDTO userDTO;
    public static String encode(String rawPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(userDTO.getPassword());
    }

}
