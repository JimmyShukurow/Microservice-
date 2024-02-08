package io.smartir.smartir.website.service;

import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.model.TagRequest;
import io.smartir.smartir.website.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

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

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}
