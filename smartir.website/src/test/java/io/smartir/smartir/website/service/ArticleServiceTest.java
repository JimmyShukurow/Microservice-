package io.smartir.smartir.website.service;

import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.repository.ArticleRepository;
import io.smartir.smartir.website.repository.TagRepository;
import io.smartir.smartir.website.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Array;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class ArticleServiceTest extends HelperFunctions {


    @Autowired
    public TypeRepository typeRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public ArticleRepository articleRepository;

//    @Autowired
//    public ArticleService articleService;


    @Test
    public void addArticle() throws Exception {
        //given
        var imageFile = createTestImage();
        var bannerImage = createTestImage();
        var title = "test";
        ArticleContentsModel model = new ArticleContentsModel("test", "test");
        var summary = "test summary";
//        articleService.addArticle(imageFile, title, bannerImage, List.of(model), List.of(1), summary);
        //when
        var expected = articleRepository.findAll();
        //then
        assertThat(expected.size()).isEqualTo(0);
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