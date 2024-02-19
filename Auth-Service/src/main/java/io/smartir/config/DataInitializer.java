package io.smartir.config;

import io.smartir.entity.UserEntity;
import io.smartir.helper.HelperFunctions;
import io.smartir.repository.RoleRepository;
import io.smartir.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;

@Component
public class DataInitializer extends HelperFunctions implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

//    Running this codes here because cant create user with hashed password in data.sql
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByEmail("admin").isPresent() ) return;
        UserEntity admin = new UserEntity();
        admin.setName("admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword(hashPassword("admin"));
        admin.setRoles(Set.of(roleRepository.findByName("ADMIN").get()));
        admin.setCreatedAt(OffsetDateTime.now());
        userRepository.save(admin);
    }
}
