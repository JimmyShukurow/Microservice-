package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.model.Article;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.model.ArticleItem;
import io.smartir.smartir.website.requests.ArticleFilterRequest;
import io.smartir.smartir.website.service.ArticleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                             @RequestPart(value = "tags", required = false) List<Integer> tags,
                             @RequestPart(value = "summary",required = true) String summary) throws Exception {
       return articleService.addArticle(imageFile,title,bannerImageFile,articleContentsModel,tags,summary);
    }

    @PostMapping(value = "update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String updateArticle(@RequestPart(value = "id") int id,
                             @RequestPart(value = "image", required = false) MultipartFile imageFile,
                             @RequestPart(value = "title", required = false) String title,
                             @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImageFile,
                             @RequestPart(value = "context", required = false) List<ArticleContentsModel> articleContentsModel,
                             @RequestPart(value = "tags", required = false) List<Integer> tags,
                             @RequestPart(value = "summary",required = true) String summary) throws Exception {
        return articleService.updateArticle(id,imageFile,title,bannerImageFile,articleContentsModel,tags,summary);
    }

    @PostMapping(value = "get-all")
    public ResponseEntity<Article>  getArticles(@RequestBody ArticleFilterRequest articleFilterRequest){
        var result=articleService.getArticles(articleFilterRequest);
        return ResponseEntity.ok(Article.toArticle(result));
    }

    @GetMapping(value = "get-all-articles")
    public ResponseEntity<List<ArticleItem>>  getAllArticles(){
        var result=articleService.getAllArticles();
        return ResponseEntity.ok(result.stream().map(ArticleItem::toArticleItem).toList());
    }

    @DeleteMapping(value = "delete/{articleId}")
    public String deleteArticle(@PathVariable int articleId){
       return articleService.deleteArticle(articleId);
    }
    @GetMapping("status")
    public String getStatus() {
        return "Filter-service is working";
    }
}
