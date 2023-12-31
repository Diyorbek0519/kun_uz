package com.example.security;

import com.example.dto.JwtDTO;
import com.example.exp.UnAuthorizedException;
import com.example.util.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
//@Component
public class JWTFilter extends GenericFilter {
    //    public static final Map<String, String> openURL = new HashMap<>();
//    {
//        openURL.put("/api/v1/auth/login", "");
//    }
    //@Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader("Authorization");

        //        if (openURL.containsKey(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Found." +
                    "");
            return;
        }
        String token = authHeader.substring(7);
        JwtDTO jwtDto;
        try {
            jwtDto = JWTUtil.decode(token);
        } catch (UnAuthorizedException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Message", "Token Not Valid");
            return;
        }
        request.setAttribute("id", jwtDto.getId());
        request.setAttribute("role", jwtDto.getRole());
        filterChain.doFilter(request, response);
    }
}
