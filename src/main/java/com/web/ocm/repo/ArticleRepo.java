package com.web.ocm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.web.ocm.entities.Article;


public interface ArticleRepo extends JpaRepository<Article, Long> , JpaSpecificationExecutor<Article> {
	
	Article findByArticleId(int artId);
}
