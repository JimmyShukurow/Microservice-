package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.model.TagRequest;
import io.smartir.smartir.website.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("add")
    public String addTag(@RequestBody TagRequest tagRequest) {
        return tagService.addTag(tagRequest);
    }

    @GetMapping("get-all")
    public List<Tag> getTags() {
        return tagService.getTags();
    }


}


