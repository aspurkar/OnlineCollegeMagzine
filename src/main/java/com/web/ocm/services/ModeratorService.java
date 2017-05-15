/**
 * 
 */
package com.web.ocm.services;

import com.web.ocm.dto.ModeratorDto;
import com.web.ocm.dto.UserDto;

/**
 * @author Aditya
 *
 * Service interface for Moderator
 */
public interface ModeratorService {

	/**
	 * @since 0.0.1
	 * @param userId
	 * @return ModeratorDto
	 * To get moderator with given user id from database.
	 */
	ModeratorDto getModeratorByUserId(int userId);

	/**
	 * @since 0.0.1
	 * @param userDto
	 * @return boolean
	 * To update moderator profile for given moderator id, return true if success, else false.
	 */
	boolean updateModerator(UserDto userDto);

}
