package com.kobe.oauth2.repository;

import com.kobe.oauth2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
