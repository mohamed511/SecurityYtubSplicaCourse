package com.test.secyoutube.security.filters;

import com.test.secyoutube.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager manager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        CustomAuthentication customAuthentication = new CustomAuthentication(authorization, null);
        try {
            Authentication result = manager.authenticate(customAuthentication);
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);// set the result in Security Context
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (AuthenticationException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}


//@Component
//public class CustomAuthenticationFilter implements Filter {
//    @Autowired
//    private AuthenticationManager manager;
//
//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        var httpRequest = (HttpServletRequest) request;
//        var httpResponse = (HttpServletResponse) response;
//
//        String authorization = httpRequest.getHeader("Authorization");
//        CustomAuthentication customAuthentication = new CustomAuthentication(authorization, null);
//        try {
//            Authentication result = manager.authenticate(customAuthentication);
//            if (result.isAuthenticated()) {
//                SecurityContextHolder.getContext().setAuthentication(result);// set the result in Security Context
//                filterChain.doFilter(request, response);
//            } else {
//                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            }
//        } catch (AuthenticationException ex) {
//            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
//    }
//}
