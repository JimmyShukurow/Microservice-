package io.smartir.smartir.website.repository;


import io.smartir.smartir.website.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findByTitleContainsAllIgnoreCase(String title);
}