package io.smartir.smartir.website.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class Tag{
    private List<TagItem> tags;
    private int currentPage;
    private int totalArticles;
    private int size;
    private int totalPages;

    public static Tag toTag(Page<io.smartir.smartir.website.entity.Tag> tagPage) {
        Tag tag = new Tag();
        tag.setTags(tagPage.getContent().stream().map(TagItem::toTag).toList());
        tag.setCurrentPage(tagPage.getNumber());
        tag.setTotalArticles((int)tagPage.getTotalElements());
        tag.setSize(tagPage.getSize());
        tag.setTotalPages(tagPage.getTotalPages());
        return tag;
    }
}



