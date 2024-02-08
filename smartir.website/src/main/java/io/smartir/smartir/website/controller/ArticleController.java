package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.entity.Article;
import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.model.ArticleFilterRequest;
import io.smartir.smartir.website.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addArticle(@RequestPart(value = "image", required = false) MultipartFile imageFile,
                             @RequestPart(value = "title", required = false) String title,
                             @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImageFile,
                             @RequestPart(value = "context", required = false) List<ArticleContentsModel> articleContentsModel,
                             @RequestPart(value = "tags", required = false) List<Integer> tags) throws Exception {
       return articleService.addArticle(imageFile,title,bannerImageFile,articleContentsModel,tags);
    }

    @PostMapping(value = "get-all")
    public ResponseEntity<Page<Article>>  getArticles(@RequestBody ArticleFilterRequest articleFilterRequest){
        Pageable pageable = PageRequest.of(articleFilterRequest.getPage(), articleFilterRequest.getSize());
        var result=articleService.getArticles(articleFilterRequest.getTagID(),articleFilterRequest.getSearch(),pageable);
        return ResponseEntity.ok(result);
    }
}
