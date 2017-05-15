/**
 * 
 */
package com.web.ocm.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.ocm.dto.ArticleDto;
import com.web.ocm.dto.SearchArticlesDto;
import com.web.ocm.entities.Article;
import com.web.ocm.entities.RefCategory;
import com.web.ocm.entities.User;
import com.web.ocm.repo.ArticleRepo;
import com.web.ocm.repo.RefCategoryRepo;
import com.web.ocm.repo.UserRepo;
import com.web.ocm.services.ArticleService;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RefCategoryRepo catRepo;

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#getArticles()
	 */
	@Override
	public List<Article> getArticles() {

		@SuppressWarnings("unchecked")
		List<Article> articles =  (List<Article>)entityManager
		.createNativeQuery("select * from articles a order by a.article_id desc limit 10", Article.class)
		.getResultList();
		if(articles.size() > 0){
			return articles;
		}
		return new ArrayList<Article>();
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#getArticleById()
	 */
	@Override
	public Article getArticleById(String artId) {

		Article artDto = new Article();

		Article art = articleRepo.findByArticleId(Integer.parseInt(artId));

		if(art != null){
			art.setNumberOfReads(art.getNumberOfReads()+1);
			articleRepo.saveAndFlush(art);

			Hibernate.initialize(art.getUser());
			Hibernate.initialize(art.getRefCategory());
			return art;
		}else{
			return null;
		}

	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#countArticles()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int countArticles(){
		List<BigInteger> noOfArticles = new ArrayList<BigInteger>();
		try {
			noOfArticles =  entityManager
					.createNativeQuery("select count(*) from articles").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int result = ((BigInteger) noOfArticles.get(0)).intValue();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#searchArticles()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> searchArticles(SearchArticlesDto searchArtDto) {

		List<Article> articles = null;

		if((searchArtDto.getArticleId() == -1 || searchArtDto.getArticleId() == 0) &&
				(searchArtDto.getHeading() == null || searchArtDto.getHeading().equals("")) && 
				(searchArtDto.getCategory() == 0)){
			articles = entityManager.createNativeQuery("select * from articles a order by a.article_id desc limit 3", Article.class).getResultList();
		}else{
			articles = initialSearchArticles(searchArtDto);
		}
		if(articles != null && articles.size() > 0){
			return articles;
		}
		return new ArrayList<Article>();
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#updateArticle()
	 */
	@Override
	public boolean updateArticle(Article artDto, int userId, String catId) {

		try {
			Article art = articleRepo.findByArticleId(artDto.getArticleId());
			User user = userRepo.findByUserId(userId);
			RefCategory category = catRepo.findByCategoryId(Integer.parseInt(catId));

			art.setArticleBody(artDto.getArticleBody());
			art.setRefCategory(category);
			art.setDate(new Date());
			art.setHeading(artDto.getHeading());
			art.setUser(user);

			articleRepo.saveAndFlush(art);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#approveArticle()
	 */
	@Override
	public boolean approveArticle(String artId) {

		try {
			Article art = articleRepo.findByArticleId(Integer.parseInt(artId));
			art.setPostStatus(1); //1 - Approved

			articleRepo.saveAndFlush(art);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#removeArticle()
	 */
	@Override
	public boolean removeArticle(String artId) {
		try {
			Article art = articleRepo.findByArticleId(Integer.parseInt(artId));

			articleRepo.delete(art);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#postSearchArticles()
	 */
	@Override
	@Transactional
	public Page<Article> postSearchArticles(SearchArticlesDto artDto, Integer pageNumber){

		int PAGE_SIZE = 3;	
		int pn = pageNumber.intValue();	

		Page<Article> resultList = null;
		PageRequest request =   new PageRequest(pn - 1, PAGE_SIZE, Sort.Direction.DESC, "articleId");	
		RefCategory cat = null;
		if(artDto.getCategory() != 0){
			cat = catRepo.findByCategoryId(artDto.getCategory());
		}

		Specifications<Article>  spec  =  Specifications.where(
				getArticlesBySearchCriteria(artDto.getArticleId(),
						artDto.getHeading(), cat));
		resultList = articleRepo.findAll(spec ,request);
		return resultList;
	}

	public List<Article> initialSearchArticles(SearchArticlesDto artDto){

		List<Article> resultList = null;
		RefCategory cat = null;
		if(artDto.getCategory() != 0){
			cat = catRepo.findByCategoryId(artDto.getCategory());
		}

		Specifications<Article>  spec  =  Specifications.where(
				getArticlesBySearchCriteria(artDto.getArticleId(),
						artDto.getHeading(), cat));
		resultList = articleRepo.findAll(spec);

		return resultList;
	}
	public Specification<Article> getArticlesBySearchCriteria(int articleId, String heading, RefCategory cat){


		return new Specification<Article>(){

			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				Predicate predicate = builder.conjunction();

				if((articleId == -1 || articleId == 0) &&
						(heading == null || heading.equals("")) && 
						(cat == null)){
					return predicate;
				}
				if(articleId != -1 && articleId != 0) {
					predicate.getExpressions().add(builder.and(builder.equal(root.<Integer>get("articleId"), articleId)));	
				}
				if(heading != null && !heading.equals("")) {
					predicate.getExpressions().add(builder.and(builder.equal(root.<String>get("heading"), heading.trim())));	
				}
				if(cat != null) {
					predicate.getExpressions().add(builder.and(builder.equal(root.<RefCategory>get("refCategory"), cat)));	
				}

				return predicate;
			}

		};
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#getCategoryList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RefCategory> getCategoryList() {
		List<RefCategory> catList = null;
		try {
			catList =  (List<RefCategory>)entityManager
					.createNativeQuery("select * from ref_category a order by a.category_id asc", RefCategory.class).getResultList();
			if(catList != null && catList.size()>0){
				return catList;
			}
		} catch (Exception e) {
			logger.error("Error: While getting category list.\n" + e.getMessage());
		}
		return new ArrayList<RefCategory>();
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ArticleService#saveArticle()
	 */
	@Override
	public boolean saveArticle(ArticleDto articleDto,int userId) {
		Article article  =  new Article();
		try {
			article.setHeading(articleDto.getHeading());
			article.setArticleBody(articleDto.getArticleBody());
			article.setUser(userRepo.findByUserId(userId));
			article.setDate(new Date());
			article.setPostStatus(1);
			article.setNumberOfReads(0l);
			articleRepo.saveAndFlush(article);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
