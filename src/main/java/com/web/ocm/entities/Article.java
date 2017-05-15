package com.web.ocm.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the articles database table.
 * 
 */
@Entity
@Table(name="articles")
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ARTICLE_ID")
	private int articleId;

	@Lob
	@Column(name="ARTICLE_BODY")
	private String articleBody;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String heading;

	@Column(name="NUMBER_OF_READS")
	private Long numberOfReads;

	@Column(name="POST_STATUS")
	private int postStatus;

	private String tags;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	//bi-directional many-to-one association to RefCategory
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID")
	private RefCategory refCategory;

	public Article() {
	}

	public int getArticleId() {
		return this.articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleBody() {
		return this.articleBody;
	}

	public void setArticleBody(String articleBody) {
		this.articleBody = articleBody;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHeading() {
		return this.heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Long getNumberOfReads() {
		return this.numberOfReads;
	}

	public void setNumberOfReads(Long numberOfReads) {
		this.numberOfReads = numberOfReads;
	}

	public int getPostStatus() {
		return this.postStatus;
	}

	public void setPostStatus(int postStatus) {
		this.postStatus = postStatus;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RefCategory getRefCategory() {
		return this.refCategory;
	}

	public void setRefCategory(RefCategory refCategory) {
		this.refCategory = refCategory;
	}

}