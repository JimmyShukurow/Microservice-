package io.smartir.smartir.website.service;

import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.entity.Article;
import io.smartir.smartir.website.exceptions.ArticleWithThisTitleExistsException;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.repository.ArticleRepository;
import io.smartir.smartir.website.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ArticleServiceTest extends HelperFunctions {


    @Mock
    private FileService fileService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private   ArticleRepository articleRepository;


    public  ArticleService articleService;


    @BeforeEach
    void setUp() {
        articleService = new ArticleService(fileService, articleRepository, tagRepository);
    }

    @Test
    @DisplayName("Add article success option ")
    public void addArticle() throws Exception {
        //given
        var imageFile = createTestImage();
        var bannerImage = createTestImage();
        var title = "test";
        ArticleContentsModel model = new ArticleContentsModel("test", "test");
        var summary = "test summary";

        //when
        when(articleRepository.findByTitleContainsAllIgnoreCase(title)).thenReturn(Optional.empty());
        when(fileService.moveImgFile(imageFile)).thenReturn(Path.of("image/path"));
        when(fileService.moveBannerImageFile(bannerImage)).thenReturn(Path.of("bannerImage/path"));
        when(articleRepository.save(Mockito.any(Article.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        var expected = articleService.addArticle(imageFile, title, bannerImage, List.of(model), List.of(), summary);

        //then
        assertThat(expected).isEqualTo("Article added");
        verify(articleRepository, times(1)).save(Mockito.any(Article.class));

    }

    @Test
    @DisplayName("Add article throws exception and not saving artice")
    public void addArticleThrowException() throws Exception {
        //given
        var imageFile = createTestImage();
        var bannerImage = createTestImage();
        var title = "test";
        ArticleContentsModel model = new ArticleContentsModel("test", "test");
        var summary = "test summary";

        //when
        when(articleRepository.findByTitleContainsAllIgnoreCase(title)).thenReturn(Optional.of(new Article()));

        //then
        assertThatThrownBy(() -> {
            articleService.addArticle(imageFile, title, bannerImage, List.of(model), List.of(), summary);

        }).isInstanceOf(ArticleWithThisTitleExistsException.class);
        verify(articleRepository, never()).save(Mockito.any(Article.class));

    }

    @Test
    public void updateArticle() {

        //given
        //when
        //then
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