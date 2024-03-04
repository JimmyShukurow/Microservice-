package io.smartir.smartir.website.repository;

import io.smartir.smartir.website.HelperFunctions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class ArticleRepositoryTest extends HelperFunctions {

    @Autowired
    private  ArticleRepository articleRepository;

    @Autowired
    private  TagRepository tagRepository;

    @Autowired
    private TypeRepository typeRepository;


    @Test
    @DisplayName("Test find By Title method of Article Repository")
    public void findByTitleContainsAllIgnoreCase() {
        //given
        var typeEntity = typeRepository.save(typeCreator());
        var tagEntity = tagRepository.save(tagCreator(typeEntity));
        var title = "test-article";

        articleRepository.save(articleCreator(List.of(tagEntity), title));

        //when
        var expected = articleRepository.findByTitleContainsAllIgnoreCase(title);
        //then
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getTitle()).isEqualTo(title);
        assertThat(expected.get().getTags().size()).isEqualTo(1);
        assertThat(expected.get().getCreatedAt()).isNotNull();
        assertThat(expected.get().getDeletedAt()).isNull();
    }
}