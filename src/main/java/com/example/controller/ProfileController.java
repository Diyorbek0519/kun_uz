package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.security.CustomUserDetails;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.create(dto, userDetails.getProfile().getId()));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.update(userDetails.getProfile().getId(), dto));
    }

    @PutMapping(value = "/updateDetail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.updateDetail(userDetails.getProfile().getId(), dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAll")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return ResponseEntity.ok(profileService.profileList(page, size));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")

    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/filter")
    public ResponseEntity<List<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO) {
        return ResponseEntity.ok(profileService.filter(filterDTO));
    }

}
