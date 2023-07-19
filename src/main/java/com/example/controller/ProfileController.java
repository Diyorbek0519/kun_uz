package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping(value = {"/create"})
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.create(dto));
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          @RequestParam("id") Integer id){
        return ResponseEntity.ok(profileService.update(id,dto));
    }
    @PutMapping(value = "/updateDetail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto,
                                                @RequestParam("id") Integer id){
        return ResponseEntity.ok(profileService.updateDetail(id,dto));
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<PageImpl<ProfileDTO>> getAll(@RequestParam("page") int page,
                                                       @RequestParam("size") int size){
        return ResponseEntity.ok(profileService.profileList(page,size));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(profileService.delete(id));
    }
    @PostMapping(value = "/filter")
    public ResponseEntity<List<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO){
        return ResponseEntity.ok(profileService.filter(filterDTO));
    }

}
