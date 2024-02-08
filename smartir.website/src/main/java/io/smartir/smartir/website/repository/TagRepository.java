package io.smartir.smartir.website.repository;


import io.smartir.smartir.website.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByNameContainsAllIgnoreCase(String name);

    List<Tag> findByIdIn(List<Integer> tagIds);
}