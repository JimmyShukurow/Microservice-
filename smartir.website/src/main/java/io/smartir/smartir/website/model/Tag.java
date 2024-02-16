package io.smartir.smartir.website.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tag {
    private int id;
    private String name;
    private Type type;

    public static Tag toTag(io.smartir.smartir.website.entity.Tag tagEntity) {
        Tag tag = new Tag();
        tag.setId(tagEntity.getId());
        tag.setName(tagEntity.getName());
        tag.setType(Type.toType(tagEntity.getType()));
        return tag;
    }
}
