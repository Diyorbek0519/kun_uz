package com.example.util;

import com.example.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityUtil {
    public String getCurrentUsername(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName= authentication.getName();
        return currentPrincipalName;
    }
   /* public UserDetails getCurrentUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }*/
    public static CustomUserDetails getCurrentUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
