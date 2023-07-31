package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.TagDTO;
import com.example.enums.ProfileRole;
import com.example.service.TagService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping(value = "/create")
    private ResponseEntity<Boolean> create(@RequestBody TagDTO tagDTO,
                                           HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(tagService.create(jwtDTO.getId(), tagDTO));
    }
}
