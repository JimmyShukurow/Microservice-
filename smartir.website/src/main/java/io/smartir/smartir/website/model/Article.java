package io.smartir.smartir.website.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class Article {
    private List<ArticleItem> articles;
    private int currentPage;
    private int totalArticles;
    private int size;
    private int totalPages;

    public static Article toArticle(Page<io.smartir.smartir.website.entity.Article> articlePage) {
        Article article = new Article();
        article.setArticles(articlePage.getContent().stream().map(ArticleItem::toArticleItem).toList());
        article.setCurrentPage(articlePage.getNumber());
        article.setTotalArticles((int) articlePage.getTotalElements());
        article.setSize(articlePage.getSize());
        article.setTotalPages(articlePage.getTotalPages());
        return article;
    }

}

@Setter
@Getter
class ArticleItem {
    private int id;
    private String bannerImage;
    private String title;
    private String image;
    private String summary;
    private List<ArticleContentsModel> contents;
    private List<TagItem> tagItems;
    public static ArticleItem toArticleItem(io.smartir.smartir.website.entity.Article article) {
        ArticleItem articleItem = new ArticleItem();
        articleItem.setId(article.getId());
        articleItem.setBannerImage(article.getBannerImage());
        articleItem.setTitle(article.getTitle());
        articleItem.setImage(article.getImage());
        articleItem.setSummary(article.getSummary());
        articleItem.setContents(article.getContents());
        articleItem.setTagItems(article.getTags().stream().map(TagItem::toTag).toList());
        return articleItem;
    }
}


