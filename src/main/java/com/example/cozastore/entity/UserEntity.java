package com.example.cozastore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;
}
