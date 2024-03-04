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




