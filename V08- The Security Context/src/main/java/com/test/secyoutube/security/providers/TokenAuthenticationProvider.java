package com.test.secyoutube.security.providers;

import com.test.secyoutube.managers.TokenManager;
import com.test.secyoutube.security.authentication.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        boolean exist = this.tokenManager.contains(token);
        if (exist) {
            return new TokenAuthentication(token, null, null);
        }
        throw new BadCredentialsException("===> invalid token from  TokenAuthenticationProvider ");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
