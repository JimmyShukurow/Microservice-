package io.smartir.smartir.website.controller;

import io.smartir.smartir.website.entity.Type;
import io.smartir.smartir.website.service.TypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("type")
public class TypeController {

   private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }


    @GetMapping("get-all")
    public List<Type> getTags() {
        return typeService.getTypes();
    }


}


