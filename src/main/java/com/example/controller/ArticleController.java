package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.security.CustomUserDetails;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/article")
public class
ArticleController {
    @Autowired
    protected ArticleService articleService;
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(value = "/create")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO articleDTO) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleService.create( userDetails.getProfile().getId(), articleDTO));
    }
    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO articleDTO) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleService.update(userDetails.getProfile().getId(), articleDTO));
    }
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("articleId") String articleId) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleService.delete(userDetails.getProfile().getId(), articleId));
    }
    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping(value = "/changeStatus")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("articleId") String articleId) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleService.changeStatus(userDetails.getProfile().getId(), articleId));
    }

    @GetMapping(value = "/open/getLast")
    public ResponseEntity<List<ArticleDTO>> getLast(@RequestParam("articleTypeId") Integer articleTypeId,
                                                    @RequestParam("limit") int limit) {
        return ResponseEntity.ok(articleService.getLastArticlesByTypes(articleTypeId, limit));
    }

    @GetMapping(value = "/open/notInList")
    public ResponseEntity<List<ArticleDTO>> notInList(@RequestBody List<String> articleId) {
        return ResponseEntity.ok(articleService.getLast8ByGivenList(articleId));
    }

    @GetMapping(value = "/open/getByIdLang")
    public ResponseEntity<ArticleDTO> getByIdAndLang(@RequestParam("articleId") String id,
                                                     @RequestParam("lang") Language language) {
        return ResponseEntity.ok(articleService.getArticleByIdAndLang(id, language));
    }

    @GetMapping(value = "/open/getLast4ArticlesExceptGivenId")
    public ResponseEntity<List<ArticleDTO>> getLast4ExceptGivenId(@RequestParam("articleId") String id,
                                                                  @RequestBody List<Integer> typeId) {
        return ResponseEntity.ok(articleService.getLast4ByTypesAndExceptGivenArticle(typeId, id));
    }

    @GetMapping(value = "/open/getMostReadArticle")
    public ResponseEntity<ArticleDTO> getMostReadArticle() {
        return ResponseEntity.ok(articleService.getMostReadArtcle());
    }

    @GetMapping(value = "/open/getLast4ArticlesByTagId")
    public ResponseEntity<List<ArticleDTO>> getLast4ArticlesByTagId(@RequestParam("tagId") Integer id) {
        return ResponseEntity.ok(articleService.getLast4ArticlesByTagId(id));
    }

    @GetMapping(value = "/open/getLast5ArticlesByTypeAndRegion")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticlesByTypeAndRegion(@RequestParam("regionId") Integer regionId,
                                                                            @RequestBody List<Integer> typeId) {
        return ResponseEntity.ok(articleService.getLast5ArticlesByTypeAndRegion(regionId, typeId));
    }

    @GetMapping(value = "/open/getArticlesByRegionId")
    public ResponseEntity<PageImpl<ArticleDTO>> getArticlesByRegionId(@RequestParam("regionId") Integer regionId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.getArticleListByRegionId(regionId, page - 1, size));
    }

    @GetMapping(value = "/open/getArticlesByCategoryId")
    public ResponseEntity<PageImpl<ArticleDTO>> getArticlesByCategoryId(@RequestParam("categoryId") Integer categoryId,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.getArticleListByCategoryId(categoryId, page - 1, size));
    }

    @GetMapping(value = "/open/getArticlesByCt")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCt(@RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLast5ArticleByCt(categoryId));
    }

}
