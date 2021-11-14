package com.test.secyoutube.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // here you implement the authentication logic
        // if the request is authentication you should return here
        // an fully authenticated Authentication instance

        // if the request is not authenticated you should throw AuthenticationException


        // the Authentication isn't supported by this provider -> return null
        // (tell AuthenticationManager to try find another AuthenticationProvider)
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (user != null) {
            if (this.passwordEncoder.matches(password, user.getPassword())) {
                var a = new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
                return a;
            }
        }
        throw new BadCredentialsException("Invalid Credential");
    }

    /**
     * called by the AuthenticationManager  before call authenticate method
     **/
    @Override
    public boolean supports(Class<?> authType) {// this param is the type of the Authentication

        // mean the AuthenticationManager will call this provider if the type of the Authentication is UsernamePasswordAuthenticationToken
        return UsernamePasswordAuthenticationToken.class.equals(authType);
    }
}
