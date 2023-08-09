package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.security.CustomUserDetails;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/create")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO regionDTO) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        userDetails.getProfile().getId();
        return ResponseEntity.ok(regionService.create(regionDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDTO regionDTO
                                          ) {

        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PutMapping(value = "/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id
                                          ) {
        return ResponseEntity.ok(regionService.delete(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @GetMapping(value = "/admin/getAll")
    public ResponseEntity<List<RegionDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping(value = "/getByLang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam("lang") Language language) {
        return ResponseEntity.ok(regionService.getByLang(language));
    }

}

