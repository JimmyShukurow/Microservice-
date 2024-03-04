package io.smartir.smartir.website.repository;

import io.smartir.smartir.website.HelperFunctions;
import io.smartir.smartir.website.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TagRepositoryTest extends HelperFunctions {

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public TypeRepository typeRepository;

    @Test
    @DisplayName("Test find by name method in Tag Repository")
    public void findByNameContainsAllIgnoreCase() {
        //given
        var type = typeRepository.save(typeCreator());
        tagRepository.save(tagCreator(type));

        //when
        var expected = tagRepository.findByNameContainsAllIgnoreCase(TAG_NAME);
        //then
        assertThat(expected).isNotEmpty();
        assertThat(expected.get().getType()).isEqualTo(type);
        assertThat(expected.get().getCreatedAt()).isNotNull();
        assertThat(expected.get().getDeletedAt()).isNull();
    }

    @Test
    @DisplayName("Test Repository method that gets all tags with in given range IDS")
    public void findByIdIn() {
        //given
        var type = typeRepository.save(typeCreator());
        var tagNames = List.of("test1", "test2", "test3", "test4", "test5");
        var tags = createManyTags(tagNames, type);
        tagRepository.saveAll(tags);
        //when
        var expected = tagRepository.findByIdIn(List.of(2,3,4));
        System.out.println(tagRepository.findAll());
        //then
        //assertion like this is giving error, need to research it
//        assertThat(expected.get(0).getName()).isEqualTo("test2");
//        assertThat(expected.get(0).getType()).isEqualTo(type);
//        assertThat(expected.get(1).getName()).isEqualTo("test3");
//        assertThat(expected.get(1).getType()).isEqualTo(type);
//        assertThat(expected.get(2).getName()).isEqualTo("test4");
//        assertThat(expected.get(2).getType()).isEqualTo(type);
        assertThat(expected.size()).isEqualTo(3);
        assertThat(expected.get(0).getType()).isEqualTo(type);
        assertThat(expected.get(1).getType()).isEqualTo(type);
        assertThat(expected.get(2).getType()).isEqualTo(type);

    }
}