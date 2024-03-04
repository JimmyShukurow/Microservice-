package io.smartir.smartir.website.service;

import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



class ArticleServiceTest extends HelperFunctions {


//    @Autowired
//    public TypeRepository typeRepository;
//
//    @Autowired
//    public TagRepository tagRepository;

    @Autowired
    public  ArticleRepository articleRepository;

    @MockBean
    @Autowired
    public  ArticleService articleService;


    @Test
    public void addArticle() throws Exception {
        //given
//        var type = typeRepository.save(typeCreator());
//        var tag = tagRepository.save(tagCreator(type));
//        var imageFile = createTestImage();
//        var bannerImage = createTestImage();
//        System.out.println("image is =>" + imageFile);
//        var title = "test";
//        ArticleContentsModel model = new ArticleContentsModel("test", "test");
//        var summary = "test summary";
//        articleService.addArticle(imageFile, title, bannerImage, List.of(model), List.of(1), summary);
//        //when
//        var expected = articleRepository.findAll();
//        //then
//        assertThat(expected.size()).isEqualTo(0);
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