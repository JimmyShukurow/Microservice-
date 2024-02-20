package io.smartir.smartir.website.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Type {
    private int id;
    private String name;

    public static Type toType(io.smartir.smartir.website.entity.Type typeEntity) {
        Type type = new Type();
        type.setId(typeEntity.getId());
        type.setName(typeEntity.getName());
        return type;
    }

}
