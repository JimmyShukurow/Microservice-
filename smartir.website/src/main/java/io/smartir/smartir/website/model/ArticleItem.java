package io.smartir.smartir.website.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleItem {
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
