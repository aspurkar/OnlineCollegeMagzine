package com.web.ocm.dto;

import java.io.Serializable;
import java.util.Date;

import com.web.ocm.entities.Article;


public class ArticleDto implements Serializable{

	private static final long serialVersionUID = 1L;

	
	private int articleId;
	private int postStatus;
	private long userId;
	
	private String categoryId;
	private String articleBody;
	private String heading;
	private String tags;
	private String uName;
	
	private Date date;
	
	private long numberOfReads;

	/**
	 * @return the articleId
	 */
	public int getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the postStatus
	 */
	public int getPostStatus() {
		return postStatus;
	}

	/**
	 * @param postStatus the postStatus to set
	 */
	public void setPostStatus(int postStatus) {
		this.postStatus = postStatus;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the articleBody
	 */
	public String getArticleBody() {
		return articleBody;
	}

	/**
	 * @param articleBody the articleBody to set
	 */
	public void setArticleBody(String articleBody) {
		this.articleBody = articleBody;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the uName
	 */
	public String getuName() {
		return uName;
	}

	/**
	 * @param uName the uName to set
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the numberOfReads
	 */
	public long getNumberOfReads() {
		return numberOfReads;
	}

	/**
	 * @param numberOfReads the numberOfReads to set
	 */
	public void setNumberOfReads(long numberOfReads) {
		this.numberOfReads = numberOfReads;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
