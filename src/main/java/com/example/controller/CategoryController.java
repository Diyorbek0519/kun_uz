package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO,
                                              HttpServletRequest request){
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CategoryDTO categoryDTO,
                                         HttpServletRequest request){
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id,categoryDTO));
    }
    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request){
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(HttpServletRequest request){
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }
    @GetMapping(value = "/getByLang")
    public ResponseEntity<List<CategoryDTO>> getByLang(@RequestParam("lang") Language language,
                                                      HttpServletRequest request){
        SecurityUtil.hasRole(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getByLang(language));
    }
}
