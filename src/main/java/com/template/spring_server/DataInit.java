package com.template.spring_server;

import com.template.spring_server.model.user.User;
import com.template.spring_server.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {

    @Autowired
    UserRepository users;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (users.count() == 0)
            this.users.save(new User("user", this.passwordEncoder.encode("password")));
    }
}