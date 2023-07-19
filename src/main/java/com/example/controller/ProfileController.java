package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping(value = {"/create"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             @RequestHeader("Authorization") String authToken){
        JwtDTO jwtDTO=SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto,jwtDTO.getId()));
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          @RequestHeader("Authorization") String authToken){
        JwtDTO jwtDTO=SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(jwtDTO.getId(), dto));
    }
    @PutMapping(value = "/updateDetail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto,
                                                @RequestHeader("Authorization") String authToken){
        JwtDTO jwtDTO =SecurityUtil.hasRole(authToken,ProfileRole.USER);
        return ResponseEntity.ok(profileService.updateDetail(jwtDTO.getId(), dto));
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.profileList(page,size));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }
    @GetMapping(value = "/filter")
    public ResponseEntity<List<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO,
                                                   @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.filter(filterDTO));
    }

}
