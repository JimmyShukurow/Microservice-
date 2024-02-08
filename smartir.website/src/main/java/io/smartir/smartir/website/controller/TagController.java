package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.model.ArticleFilterRequest;
import io.smartir.smartir.website.service.TagService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("add")
    public String addTag(@RequestPart String name) {
        return tagService.addTag(name);
    }

    @GetMapping("get-all")
    public List<Tag> getTags() {
        return tagService.getTags();
    }


}


