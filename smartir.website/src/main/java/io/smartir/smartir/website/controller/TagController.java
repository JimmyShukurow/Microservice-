package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.model.Tag;
import io.smartir.smartir.website.requests.TagRequest;
import io.smartir.smartir.website.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("tag")
@Slf4j
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("add")
    public String addTag(@RequestBody TagRequest tagRequest) {
        return tagService.addTag(tagRequest);
    }

    @PostMapping("get-all")
    public ResponseEntity<Tag> getTags(@RequestBody TagRequest request) {
        return ResponseEntity.ok(Tag.toTag(tagService.getTags(request.getPage(), request.getSize())));
    }
    @GetMapping("status")
    public String getStatus() {
        return "Filter-service is working";
    }
}


