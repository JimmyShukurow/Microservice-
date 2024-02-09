package io.smartir;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String email;
    private String token;

    public User toUser(UserEntity entity) {
        var user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());

        return user;
    }
}
