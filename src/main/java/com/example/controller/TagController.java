package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.TagDTO;
import com.example.enums.ProfileRole;
import com.example.security.CustomUserDetails;
import com.example.service.TagService;
import com.example.util.SecurityUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(value = "/create")
    private ResponseEntity<Boolean> create(@RequestBody TagDTO tagDTO) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(tagService.create(userDetails.getProfile().getId(), tagDTO));
    }
}
