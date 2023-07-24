package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping(value = "/admin/create")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO regionDTO,
                                           HttpServletRequest request) {
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(regionDTO));
    }

    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDTO regionDTO,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }

    @PutMapping(value = "/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }

    @GetMapping(value = "/admin/getAll")
    public ResponseEntity<List<RegionDTO>> getAll( HttpServletRequest request) {
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping(value = "/getByLang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam("lang") Language language,
                                                     HttpServletRequest request) {
        SecurityUtil.hasRole(request,null);
        return ResponseEntity.ok(regionService.getByLang(language));
    }

}

