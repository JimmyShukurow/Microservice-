package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.helper.HelperFunctions;
import io.smartir.smartir.website.requests.TagRequest;
import io.smartir.smartir.website.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService extends HelperFunctions {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public String addTag(TagRequest tagRequest) {
        Optional<Tag> tagCheck = tagRepository.findByNameContainsAllIgnoreCase(tagRequest.getName());
        if (tagCheck.isEmpty()) {
            Tag tag = new Tag();
            tag.setName(tagRequest.getName());
            tag.setType(tagRequest.getType());
            tagRepository.save(tag);
            return "Tag added";
        } else {
            return "This tag already exist";
        }
    }

    public Page<Tag> getTags(TagRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        var tags = tagRepository.findAll();
        System.out.println(tags.size());
        return makingPagination(tags, pageable);
    }
}
