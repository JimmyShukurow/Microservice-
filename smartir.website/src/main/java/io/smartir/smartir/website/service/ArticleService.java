package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Article;
import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.exceptions.ArticleWithThisTitleExistsException;
import io.smartir.smartir.website.helper.HelperFunctions;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.requests.ArticleFilterRequest;
import io.smartir.smartir.website.repository.ArticleRepository;
import io.smartir.smartir.website.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ArticleService extends HelperFunctions {

    private final FileService fileService;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleService(FileService fileService, ArticleRepository articleRepository, TagRepository tagRepository) {
        this.fileService = fileService;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    public String addArticle(MultipartFile imageFile, String title, MultipartFile bannerImageFile, List<ArticleContentsModel> articleContentsModel, List<Integer> tagIDs, String summary) throws Exception {
        Optional<Article> articleCheck = articleRepository.findByTitleContainsAllIgnoreCase(title);

        if (articleCheck.isPresent()) throw new ArticleWithThisTitleExistsException();

        Path imagePath = fileService.moveImgFile(imageFile);
        Path bannerImagePath = fileService.moveBannerImageFile(bannerImageFile);

        List<Tag> tags = getTags(tagIDs);
        Article article = new Article();
        article.setImage(imagePath.toString());
        article.setBannerImage(bannerImagePath.toString());
        article.setTitle(title);
        article.setContents(articleContentsModel);
        article.setSummary(summary);
        article.setTags(tags);
        articleRepository.save(article);

        return "Article added";

    }

    public String updateArticle(int id, MultipartFile imageFile, String title, MultipartFile bannerImageFile,
                                List<ArticleContentsModel> articleContentsModel, List<Integer> tagIDs, String summary) throws Exception {
        Optional<Article> oldArticleOptional = articleRepository.findById(id);

        if (oldArticleOptional.isPresent()) {
            Article oldArticle = oldArticleOptional.get();
            Optional<Article> articleCheck = articleRepository.findByTitleContainsAllIgnoreCase(title);
            // Check if the new title is unique if changed
            if (articleCheck.isPresent() && articleCheck.get().getId() != id) {
                return "Another article with the same title exists. Update failed.";
            }

            if (imageFile != null) {
                Path imagePath = fileService.moveImgFile(imageFile);
                oldArticle.setImage(imagePath.toString());
            }
            if (bannerImageFile != null) {
                Path bannerImagePath = fileService.moveBannerImageFile(bannerImageFile);
                oldArticle.setBannerImage(bannerImagePath.toString());
            }

            // Set other attributes
            oldArticle.setTitle(title);
            oldArticle.setContents(articleContentsModel);
            oldArticle.setSummary(summary);

            // Set tags
            List<Tag> tags = getTags(tagIDs);
            oldArticle.setTags(tags);

            articleRepository.save(oldArticle);
            return "Article updated successfully.";
        } else {
            return "Article with id " + id + " not found.";
        }
    }


    public Page<Article> getArticles(ArticleFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        var articles = articleRepository.findAll(); // getting all result (deleted and not deleted)
        articles = articles.stream().filter(article -> article.deletedAt == null).toList(); //filtering NON deleted results here
        articles = filterByTag(articles, request);
        articles = filterBySearch(articles, request.getSearch());

        return makingPagination(articles, pageable);
    }


    public String deleteArticle(int id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            var articleEntity = article.get();
            articleEntity.setDeletedAt(OffsetDateTime.now());
            articleRepository.save(articleEntity);
            return "Article Deleted";
        } else {
            return "Article not found";
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

    public List<Article> filterByTag(List<Article> articles, ArticleFilterRequest request) {

        if (!request.getTagID().isEmpty())
            return articles.stream().filter(
                    article -> article.getTags().stream().anyMatch(
                            tag -> request.getTagID().contains(tag.getId())
                    )).toList();
        return articles;
    }

    public List<Article> filterBySearch(List<Article> articles, String search) {
        if (!search.isEmpty() && !search.isBlank()) {
            Predicate<Article> containsName = article ->
                    article.getTitle().toLowerCase().contains(search.toLowerCase());
            Predicate<Article> containsSummary = article ->
                    article.getSummary() != null && article.getSummary().toLowerCase().contains(search.toLowerCase());
            return articles.stream()
                    .filter(containsName.or(containsSummary))
                    .collect(Collectors.toList());
        }
        return articles;
    }


}

