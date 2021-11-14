package com.test.secyoutube.security.filters;

import com.test.secyoutube.entities.Otp;
import com.test.secyoutube.repositories.OtpRepository;
import com.test.secyoutube.security.authentication.OtpAuthentication;
import com.test.secyoutube.security.authentication.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // step 1: username & password
        // step 2: username & otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if (otp == null) {
            // step 1
            Authentication a = new UsernamePasswordAuthentication(username, password);
            a = this.authenticationManager.authenticate(a);
            // generate an otp
            String code = String.valueOf(new Random().nextInt(9999)+1000);
            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            this.otpRepository.save(otpEntity);
        } else {
            // step 2
            Authentication a = new OtpAuthentication(username, otp);
            a = this.authenticationManager.authenticate(a);
            SecurityContextHolder.getContext().setAuthentication(a);
            // issue a token
            response.setHeader("Authorization", UUID.randomUUID().toString());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
