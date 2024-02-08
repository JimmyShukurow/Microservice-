package io.smartir.smartir.website.entity;

import io.smartir.smartir.website.model.ArticleContentsModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Article {

    @Id
    @SequenceGenerator( name = "article_sequence", sequenceName = "article_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="article_sequence")
    private int id;

    String bannerImage;
    String title;
    String image;

    @ElementCollection
    List<ArticleContentsModel> contents;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "articles_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private List<Tag> tags;
}
