package com.registration.registration_login_demo.controller;

import com.registration.registration_login_demo.dto.UserDTO;
import com.registration.registration_login_demo.entity.User;
import com.registration.registration_login_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String openRegisterPanel(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register/save")
    public String registerAndSaveUser(@ModelAttribute("user") UserDTO userDTO, Model model){
        User user = userService.findByEmail(userDTO.getEmail());
        if(user!=null){
            model.addAttribute("userExists", user);
            return "register";
        }
        userService.save(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDTO> userDTOS = userService.getAllUsers();
        model.addAttribute("users", userDTOS);
        return "users";
    }
}
