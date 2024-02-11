package io.smartir.model;

import io.smartir.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private long id;
    private String name;

    public static Role toRole(RoleEntity entity) {
        var role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        return role;
    }
    public static Role toRoleOnlyName(RoleEntity entity) {
        var role = new Role();
        role.setName(role.getName());
        return role;
    }
}
