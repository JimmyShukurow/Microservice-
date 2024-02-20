package io.smartir.model;

import io.smartir.entity.RoleEntity;
import io.smartir.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private long id;
    private String name;
    private String email;
//    private String token;
    private List<String> roles;

    public static User toUser(UserEntity entity) {
        var user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setRoles(entity.getRoles().stream().map(RoleEntity::getName).toList());
        return user;
    }
}
