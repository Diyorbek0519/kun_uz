package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articleType/")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO articleTypeDTO) {
        return ResponseEntity.ok(articleTypeService.create(articleTypeDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody ArticleTypeDTO articleTypeDTO) {
        return ResponseEntity.ok(articleTypeService.update(id, articleTypeDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/getAll")
    public ResponseEntity<List<ArticleTypeDTO>> getAll() {
        return ResponseEntity.ok(articleTypeService.getAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/getByLang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@RequestBody Language language) {
        return ResponseEntity.ok(articleTypeService.getByLang(language));
    }
}
