package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Article;
import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.repository.ArticleRepository;
import io.smartir.smartir.website.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final FileService fileService;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleService(FileService fileService, ArticleRepository articleRepository, TagRepository tagRepository) {
        this.fileService = fileService;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    public String addArticle(MultipartFile imageFile, String title, MultipartFile bannerImageFile, List<ArticleContentsModel> articleContentsModel, List<Integer> tagIDs) throws Exception {
       Optional<Article> articleCheck=articleRepository.findByTitleContainsAllIgnoreCase(title);
       if (articleCheck.isEmpty()){
           Path imagePath=fileService.moveImgFile(imageFile);
           Path bannerImagePath=fileService.moveBannerImageFile(bannerImageFile);
           List<Tag> tags = getTags(tagIDs);
           Article article=new Article();
           article.setImage(imagePath.toString());
           article.setBannerImage(bannerImagePath.toString());
           article.setTitle(title);
           article.setContents(articleContentsModel);
           article.setTags(tags);
           articleRepository.save(article);
           return "Article added";
       }else
           return "This article already exist";
    }



    public Page<Article> getArticles(Collection<Integer> tagID, String q, Pageable pageable) {
        List<Article> articles = articleRepository.findAll();
        List<Article> articleResults = new ArrayList<>();
        List<Article> filteredArticles = new ArrayList<>();

        for (Article article : articles) {
            Article newArticle = new Article();
            if (tagFilter(tagID, article)) {
                newArticle.setId(article.getId());
                newArticle.setTitle(article.getTitle());
                newArticle.setImage(article.getImage());
                newArticle.setBannerImage(article.getBannerImage());
                newArticle.setTags(article.getTags());
                newArticle.setContents(article.getContents());
                articleResults.add(newArticle);
            }
        }

        if (tagID.isEmpty() && q.isEmpty()) {
            return new PageImpl<>(articles, pageable, articles.size());
        } else if (!tagID.isEmpty() && !q.isEmpty()) {
            Predicate<Article> containsName = article ->
                    article.getTitle().toLowerCase().contains(q.toLowerCase());
            filteredArticles = articleResults.stream()
                    .filter(containsName)
                    .collect(Collectors.toList());
            return new PageImpl<>(filteredArticles, pageable, filteredArticles.size());
        } else if (!tagID.isEmpty() && q.isEmpty()) {
            return new PageImpl<>(articleResults, pageable, articleResults.size());
        } else {
            return new PageImpl<>(articles, pageable, articles.size());
        }
    }



    private List<Tag> getTags(List<Integer> tagIds) throws Exception {
        List<Tag> existingTags = tagRepository.findByIdIn(tagIds);

        for (Integer tagId : tagIds) {
            boolean tagExists = existingTags.stream().anyMatch(tag -> tag.getId() == tagId);
            if (!tagExists) {
                throw new Exception("Invalid tag ID: " + tagId);
            }
        }

        return existingTags;
    }

    public boolean tagFilter(Collection<Integer> ids, Article article) {
        if (!ids.isEmpty()) {
            return article.getTags().stream()
                    .map(Tag::getId)
                    .anyMatch(ids::contains);
        }
        return true;
    }
}

