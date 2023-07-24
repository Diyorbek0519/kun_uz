package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping( "api/v1/article")
public class ArticleController {
    @Autowired
    protected ArticleService articleService;

    @PostMapping(value = "/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO articleDTO,
                                             HttpServletRequest request){
        JwtDTO jwtDTO= SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(jwtDTO.getId(),articleDTO));
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO articleDTO,
                                          HttpServletRequest request){
        JwtDTO jwtDTO=SecurityUtil.hasRole(request,ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(jwtDTO.getId(),articleDTO));
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("articleId") String articleId,
                                           HttpServletRequest request){
        JwtDTO jwtDTO =SecurityUtil.hasRole(request,ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(jwtDTO.getId(), articleId));
    }
    @PutMapping(value = "/changeStatus")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("articleId") String articleId,
                                                HttpServletRequest request){
        JwtDTO jwtDTO =SecurityUtil.hasRole(request,ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(jwtDTO.getId(),articleId));
    }
}
