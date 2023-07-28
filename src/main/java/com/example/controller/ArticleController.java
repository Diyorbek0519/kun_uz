package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {
    @Autowired
    protected ArticleService articleService;

    @PostMapping(value = "/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO articleDTO,
                                             HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(jwtDTO.getId(), articleDTO));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO articleDTO,
                                          HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(jwtDTO.getId(), articleDTO));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("articleId") String articleId,
                                          HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(jwtDTO.getId(), articleId));
    }

    @PutMapping(value = "/changeStatus")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("articleId") String articleId,
                                                HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(jwtDTO.getId(), articleId));
    }

    @GetMapping(value = "/getLast")
    public ResponseEntity<List<ArticleDTO>> getLast(@RequestParam("articleTypeId") Integer articleTypeId,
                                                    @RequestParam("limit") int limit,
                                                    HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(articleService.getLastArticlesByTypes(articleTypeId, limit));
    }

    @GetMapping(value = "/notInList")
    public ResponseEntity<List<ArticleDTO>> notInList(@RequestBody List<String> articleId,
                                                      HttpServletRequest servletRequest) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(servletRequest, null);
        return ResponseEntity.ok(articleService.getLast8ByGivenList(articleId));
    }

    @GetMapping(value = "/getByIdLang")
    public ResponseEntity<ArticleDTO> getByIdAndLang(@RequestParam("articleId") String id,
                                                     @RequestParam("lang") Language language,
                                                     HttpServletRequest servletRequest) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(servletRequest, null);
        return ResponseEntity.ok(articleService.getArticleByIdAndLang(id, language));
    }

    @GetMapping(value = "/getLast4ArticlesExceptGivenId")
    public ResponseEntity<List<ArticleDTO>> getLast4ExceptGivenId(@RequestParam("articleId") String id,
                                                                  @RequestBody List<Integer> typeId,
                                                                  HttpServletRequest servletRequest) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(servletRequest, null);
        return ResponseEntity.ok(articleService.getLast4ByTypesAndExceptGivenArticle(typeId,id));
    }
    @GetMapping(value = "/getMostReadArticle")
    public ResponseEntity<ArticleDTO> getMostReadArticle(HttpServletRequest request){
        SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(articleService.getMostReadArtcle());
    }
    @GetMapping(value = "/getLast4ArticlesByTagId")
    public ResponseEntity<List<ArticleDTO>> getLast4ArticlesByTagId(@RequestParam("tagId") Integer id,
                                                                   HttpServletRequest servletRequest) {
        SecurityUtil.hasRole(servletRequest, null);
        return ResponseEntity.ok(articleService.getLast4ArticlesByTagId(id));
    }
    @GetMapping(value = "/getLast5ArticlesByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticlesByTypeAndRegion(@RequestParam("regionId") Integer regionId,
                                                                    @RequestBody List<Integer> typeId,
                                                                    HttpServletRequest servletRequest) {
        SecurityUtil.hasRole(servletRequest, null);
        return ResponseEntity.ok(articleService.getLast5ArticlesByTypeAndRegion(regionId,typeId));
    }
    @GetMapping(value = "/getArticlesByRegionId")
    public ResponseEntity<PageImpl<ArticleDTO>> getArticlesByRegionId(@RequestParam("regionId") Integer regionId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                      HttpServletRequest request){
        SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(articleService.getArticleListByRegionId(regionId,page-1,size));
    }
    @GetMapping(value = "/getArticlesByCategoryId")
    public ResponseEntity<PageImpl<ArticleDTO>> getArticlesByCategoryId(@RequestParam("categoryId") Integer categoryId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                        HttpServletRequest request){
        SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(articleService.getArticleListByRegionId(categoryId,page-1,size));
    }
    @GetMapping(value = "/getArticlesByCt")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCt(@RequestParam("categoryId") Integer categoryId,
                                                            HttpServletRequest request){
        SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(articleService.getLast5ArticleByCt(categoryId));
    }

}
