package com.registration.registration_login_demo.service;

import com.registration.registration_login_demo.dto.UserDTO;
import com.registration.registration_login_demo.entity.User;

import java.util.List;

public interface UserService {

    public User findByName(String name);

    public User findByEmail(String email);

    public User save(UserDTO userDTO);

    public List<UserDTO> getAllUsers();
}
