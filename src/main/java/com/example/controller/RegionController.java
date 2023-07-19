package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PostMapping(value = "/create")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO regionDTO){
        return ResponseEntity.ok(regionService.create(regionDTO));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
            @RequestBody RegionDTO regionDTO){
        return ResponseEntity.ok(regionService.update(id,regionDTO));
    }
    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(regionService.delete(id));
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<RegionDTO>> getAll(){
       return ResponseEntity.ok(regionService.getAll());
    }
    @GetMapping(value = "/getByLang")
    public ResponseEntity<List<RegionDTO>> getByLang(@RequestParam("lang")Language language){
        return ResponseEntity.ok(regionService.getByLang(language));
    }

}

