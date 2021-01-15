package com.example.sigo.admin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private long id;

    private String username;

    private String password;

    private String role;

    private boolean isLocked;

}
