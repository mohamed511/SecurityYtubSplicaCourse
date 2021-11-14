package com.test.secyoutube.service;

import com.test.secyoutube.entities.User;
import com.test.secyoutube.repositories.UserRepository;
import com.test.secyoutube.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var o = this.userRepository.findUserByUsername(username);
        User u = o.orElseThrow(() -> new UsernameNotFoundException("can not find the username"));
        return new SecurityUser(u);
    }
}
