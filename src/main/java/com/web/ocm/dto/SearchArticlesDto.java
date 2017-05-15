/**
 * 
 */
package com.web.ocm.dto;

import java.io.Serializable;
import java.util.List;

import com.web.ocm.entities.RefCategory;


public class SearchArticlesDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private int articleId;
	
	private String heading;
	private List<RefCategory> categoryList;
	private int category;
	private int currentIndex;
	
//	private List<Article> resultList = new ArrayList<Article>();
	
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
	 * @return the categoryList
	 */
	public List<RefCategory> getCategoryList() {
		return categoryList;
	}
	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<RefCategory> categoryList) {
		this.categoryList = categoryList;
	}
	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	/**
	 * @return the currentIndex
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
	/**
	 * @param currentIndex the currentIndex to set
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	/**
	 * @return the resultList
	 */
//	public List<Article> getResultList() {
//		return resultList;
//	}
	/**
	 * @param resultList the resultList to set
	 */
//	public void setResultList(List<Article> resultList) {
//		this.resultList = resultList;
//	}
}
