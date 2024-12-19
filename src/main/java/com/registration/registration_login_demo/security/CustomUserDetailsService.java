package com.registration.registration_login_demo.security;

import com.registration.registration_login_demo.entity.Role;
import com.registration.registration_login_demo.entity.User;
import com.registration.registration_login_demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(
                    user.getName(), user.getPassword(), mapToAuthority(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid Username or password");
        }
    }

    public Collection<? extends GrantedAuthority> mapToAuthority(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
