package io.smartir.smartir.website.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagItem {
    private int id;
    private String name;
    private Type type;

    public static TagItem toTag(io.smartir.smartir.website.entity.Tag tagEntity) {
        TagItem tagItem = new TagItem();
        tagItem.setId(tagEntity.getId());
        tagItem.setName(tagEntity.getName());
        tagItem.setType(Type.toType(tagEntity.getType()));
        return tagItem;
    }
}