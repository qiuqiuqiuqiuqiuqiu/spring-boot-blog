package com.reljicd.service;

import com.reljicd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by kaka.li on 2018-06-26.
 */
@Component(value = "baseAuthSettingService")
@ConfigurationProperties(prefix = "auth")
public class BaseAuthSettingService {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authIsPass(String usernameSrc, String passwordSrc) {
        boolean ret = usernameSrc.compareTo(username) == 0
                && passwordSrc.compareTo(password) == 0;
        if (!ret) {
            Optional<User> user= userService.findByUsername(usernameSrc);
            if(user.isPresent()){
                return passwordEncoder.matches(passwordSrc, user.get().getPassword());
            }
        }
        return ret;
    }
}
