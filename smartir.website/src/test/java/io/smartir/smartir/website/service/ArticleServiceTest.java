package io.smartir.smartir.website.service;

import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.repository.ArticleRepository;
import io.smartir.smartir.website.repository.TagRepository;
import io.smartir.smartir.website.repository.TypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleServiceTest extends HelperFunctions {


    @Autowired
    public TypeRepository typeRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public ArticleRepository articleRepository;

    @Autowired
    public ArticleService articleService;


    @Test
    public void addArticle() {
    }

    @Test
    public void updateArticle() {
    }

    @Test
    public void getArticles() {
    }

    @Test
    public void deleteArticle() {
    }

    @Test
    public void filterByTag() {
    }

    @Test
    public void filterBySearch() {
    }
}