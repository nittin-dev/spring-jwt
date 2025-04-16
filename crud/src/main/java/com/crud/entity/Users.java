package com.crud.entity;


import com.crud.DTO.UserDTO;
import jakarta.persistence.*;
import com.crud.enums.roleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "OurUsers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;

    @Enumerated(EnumType.STRING)
    private roleEnum role;

    private String email;
    private String username;
}
