package com.template.spring_server.rest;


import com.template.spring_server.model.user.User;
import com.template.spring_server.model.user.UserRepository;
import com.template.spring_server.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository users;

    @PostMapping
    public Map<Object, Object> getToken(@RequestBody AuthRequest data) {

        String username = data.getUsername();
        User user = this.users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));

        if (!this.passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }

        String token = jwtTokenProvider.createToken(
                username,
                user.getRoles());
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return model;

    }
}