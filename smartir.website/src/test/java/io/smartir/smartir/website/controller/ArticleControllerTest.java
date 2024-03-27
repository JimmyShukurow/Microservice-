package io.smartir.smartir.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.model.ArticleContentsModel;
import io.smartir.smartir.website.requests.ArticleFilterRequest;
import io.smartir.smartir.website.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest extends HelperFunctions {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("Testing article/add endpoint in article controller")
    public void testAddArticle_withValidData() throws Exception {
        //given
        var mockImageFile = createTestImage();
        var mockBannerImage = createTestImage();
        var title = "title";
        var mockContent = List.of(new ArticleContentsModel("test", "test"));
        var mockTags = List.of(1, 2);
        var summary = "test Summary";
        //when
        when(articleService.addArticle(any(), any(), any(), any(), any(), any())).thenReturn("success");

        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/article/add")
                        .file(new MockMultipartFile("image", null, "application/json", mockImageFile.getBytes()))
                        .file(new MockMultipartFile("title", null, "application/json", title.getBytes()))
                        .file(new MockMultipartFile("bannerImage", null, "application/json", mockBannerImage.getBytes()))
                        .file(new MockMultipartFile("context", null, "application/json", new ObjectMapper().writeValueAsString(mockContent).getBytes()))
                        .file(new MockMultipartFile("tags", null, "application/json", new ObjectMapper().writeValueAsString(mockTags).getBytes()))
                        .file(new MockMultipartFile("summary", null, "application/json", summary.getBytes()))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("Testing article/update endpoint in article controller")
    public void testUpdateArticle() throws Exception {
        //given
        var mockImageFile = createTestImage();
        var mockBannerImage = createTestImage();
        var title = "title";
        var mockContent = List.of(new ArticleContentsModel("test", "test"));
        var mockTags = List.of(1, 2);
        var summary = "test Summary";
        //when
        String expected = "updated";
        when(articleService.updateArticle(
                anyInt(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any())
        ).thenReturn(expected);
        //then
        var id = "1";
        mockMvc.perform(MockMvcRequestBuilders.multipart("/article/update")
                        .file(new MockMultipartFile("id", null, "application/json", id.getBytes()))
                        .file(new MockMultipartFile("image", null, "application/json", mockImageFile.getBytes()))
                        .file(new MockMultipartFile("title", null, "application/json", title.getBytes()))
                        .file(new MockMultipartFile("bannerImage", null, "application/json", mockBannerImage.getBytes()))
                        .file(new MockMultipartFile("context", null, "application/json", new ObjectMapper().writeValueAsString(mockContent).getBytes()))
                        .file(new MockMultipartFile("tags", null, "application/json", new ObjectMapper().writeValueAsString(mockTags).getBytes()))
                        .file(new MockMultipartFile("summary", null, "application/json", summary.getBytes()))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("Test get all articles with Pagination ")
    public void getArticles() throws Exception {
        //given
        ArticleFilterRequest request = new ArticleFilterRequest();
        request.setSize(5);
        request.setPage(0);
        request.setTagID(List.of());
        request.setSearch("");
        var articles = createManyArticles();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        var result = new PageImpl<>(articles, pageable, articles.size());
        //when
        when(articleService.getArticles(request)).thenReturn(result);
        var expected = """
                {"articles":[{"id":0,"bannerImage":"test-banner-image","title":"article1","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]},{"id":0,"bannerImage":"test-banner-image","title":"article2","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]},{"id":0,"bannerImage":"test-banner-image","title":"artcile3","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]}],"currentPage":0,"totalArticles":3,"size":5,"totalPages":1}""";
        //then
        var response = mockMvc.perform(post("/article/get-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(response.getResponse().getContentAsString());

        assertThat(response.getResponse().getContentAsString()).contains("\"size\":5");
        assertThat(response.getResponse().getContentAsString()).contains("\"currentPage\":0");
        assertThat(response.getResponse().getContentAsString()).contains("\"totalArticles\":3");
        assertThat(response.getResponse().getContentAsString()).isEqualTo(expected);

    }

    @Test
    public void getAllArticles() throws Exception {
        //given
        var articles = createManyArticles();
        //when
        when(articleService.getAllArticles()).thenReturn(articles);
        var expected = """
                [{"id":0,"bannerImage":"test-banner-image","title":"article1","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]},{"id":0,"bannerImage":"test-banner-image","title":"article2","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]},{"id":0,"bannerImage":"test-banner-image","title":"artcile3","image":"test-image","summary":"summary of test article","contents":[{"text":"test","subtitle":"test"}],"tagItems":[{"id":0,"name":"tag1","type":{"id":0,"name":null}},{"id":0,"name":"tag2","type":{"id":0,"name":null}},{"id":0,"name":"tag3","type":{"id":0,"name":null}}]}]""";
        //then
        var response = mockMvc.perform(get("/article/get-all-articles"))
//                .andExpect(content().json("[{}]"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    public void deleteArticle() throws Exception {
        //given
        int articleId = 1;

        //when
        String message = "Article deleted successfully";
        when(articleService.deleteArticle(articleId)).thenReturn(message);
        //then
        mockMvc.perform(delete("/article/delete/{articleId}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @Test
    public void getStatus() throws Exception {
        mockMvc.perform(get("/article/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Filter-service is working"));

    }
}