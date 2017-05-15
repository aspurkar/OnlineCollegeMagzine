/**
 * 
 */
package com.web.ocm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.web.ocm.dto.ArticleDto;
import com.web.ocm.dto.SearchArticlesDto;
import com.web.ocm.entities.Article;
import com.web.ocm.entities.RefCategory;


public interface ArticleService {

	/**
	 * @since 0.0.1
	 * @param 
	 * @return List<Article>
	 * To get all articles from database.
	 */
	List<Article> getArticles();

	/**
	 * @since 0.0.1
	 * @param artId
	 * @return Article
	 * To get article by article id from database.
	 */
	Article getArticleById(String artId);

	/**
	 * @since 0.0.1
	 * @param searchArtDto
	 * @return List<Article>
	 * To search articles by search criteria from database.
	 */
	List<Article> searchArticles(SearchArticlesDto searchArtDto);

	/**
	 * @since 0.0.1
	 * @param artDto, userId, catId
	 * @return boolean
	 * To update article by article id in database. Return true if updated successfully, else false.
	 */
	boolean updateArticle(Article artDto, int userId, String catId);

	/**
	 * @since 0.0.1
	 * @param artId
	 * @return boolean
	 * To approve article by article id. Return true if approved successfully, else false.
	 */
	boolean approveArticle(String artId);

	/**
	 * @since 0.0.1
	 * @param artId
	 * @return boolean
	 * To remove article by article id. Return true if removed successfully, else false.
	 */
	boolean removeArticle(String artId);

	/**
	 * @since 0.0.1
	 * @param 
	 * @return List<RefCategory>
	 * To get article category list.
	 */
	List<RefCategory> getCategoryList();

	/**
	 * @since 0.0.1
	 * @param artDto, pageNumber
	 * @return Page<Article>
	 * To get article list by {@link Page}.
	 */
	Page<Article> postSearchArticles(SearchArticlesDto artDto, Integer pageNumber);

	/**
	 * @since 0.0.1
	 * @param 
	 * @return int
	 * To count no of articles.
	 */
	int countArticles();

	/**
	 * @since 0.0.1
	 * @param articleDto, parseLong
	 * @return boolean
	 * To save article by article id. Return true if save successfull, else false.
	 */
	boolean saveArticle(ArticleDto articleDto, int parseLong);


}
