/**
 * 
 */
package com.web.ocm.services;

import java.util.List;

import com.web.ocm.dto.UserDto;


public interface AdminService {

	List<UserDto> getAllUsersList();

	UserDto getUserById(String userId);
	boolean updateUser(UserDto userDto);
	boolean deleteUserById(String uid);
}
