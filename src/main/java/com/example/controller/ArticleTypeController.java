package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articleType/")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;
    @PostMapping(value = "/create")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO articleTypeDTO,
                                                 @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(articleTypeDTO));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody ArticleTypeDTO articleTypeDTO,
                                          @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.update(id,articleTypeDTO));
    }
    @PutMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.delete(id));
    }
    @PostMapping(value = "/getAll")
    public ResponseEntity<List<ArticleTypeDTO>> getAll( @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getAll());
    }
    @PutMapping(value = "/getByLang")
    public ResponseEntity<List<ArticleTypeDTO>> getByLang(@RequestBody Language language,
                                                          @RequestHeader("Authorization") String authToken){
        SecurityUtil.hasRole(authToken,ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getByLang(language));
    }
}
