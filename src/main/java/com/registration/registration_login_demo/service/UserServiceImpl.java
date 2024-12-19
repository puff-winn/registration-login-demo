package com.registration.registration_login_demo.service;

import com.registration.registration_login_demo.dto.UserDTO;
import com.registration.registration_login_demo.entity.Role;
import com.registration.registration_login_demo.entity.User;
import com.registration.registration_login_demo.repository.RoleRepository;
import com.registration.registration_login_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User(userDTO.getId(), userDTO.getFirstName()+" "+userDTO.getLastName(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), new ArrayList<>());
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            addAdminRole();
        }
        user.getRoles().add(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapUserToDto(user)).collect(Collectors.toList());
    }

    private void addAdminRole() {
        Role role = new Role();
        role.setId(101L);
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);
    }


    private UserDTO mapUserToDto(User user){
        UserDTO userDTO = new UserDTO();
        String[] names = user.getName().split(" ");
        userDTO.setFirstName(names[0]);
        userDTO.setLastName(names[1]);
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
