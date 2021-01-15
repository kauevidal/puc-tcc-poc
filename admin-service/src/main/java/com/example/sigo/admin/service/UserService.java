package com.example.sigo.admin.service;

import com.example.sigo.admin.controller.UserDTO;
import com.example.sigo.admin.model.UserEntity;
import com.example.sigo.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
        return userDTO;
    }

    public UserDTO findUser(String username) {
        UserEntity entity = userRepository.findByUsername(username);
        return new UserDTO(entity.getId(), entity.getUsername(), null, entity.getRole().name(),
                entity.isAccountNonLocked());
    }
}
