/**
 * 
 */
package com.web.ocm.dto;

import java.io.Serializable;

import com.web.ocm.entities.Moderator;


public class ModeratorDto implements Serializable{

	private static final long serialVersionUID = 2380764679321561083L;
	
	private int moderatorId;

	/**
	 * @return the moderatorId
	 */
	public int getModeratorId() {
		return moderatorId;
	}

	/**
	 * @param moderatorId the moderatorId to set
	 */
	public void setModeratorId(int moderatorId) {
		this.moderatorId = moderatorId;
	}
	
	
}
