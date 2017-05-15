package com.web.ocm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;



@Entity
@Table(name="ref_category")
@NamedQuery(name="RefCategory.findAll", query="SELECT r FROM RefCategory r")
public class RefCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CATEGORY_ID")
	private int categoryId;

	private String description;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="refCategory")
	private List<Article> articles;

	public RefCategory() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setRefCategory(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setRefCategory(null);

		return article;
	}

}