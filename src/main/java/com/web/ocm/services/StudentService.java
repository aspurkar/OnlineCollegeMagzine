package com.web.ocm.services;

import com.web.ocm.dto.StudentDto;
import com.web.ocm.dto.UserDto;

/**
 * @author Aditya
 *
 * Service interface for Student.
 */
public interface StudentService {

	/**
	 * @since 0.0.1
	 * @param userId
	 * @return StudentDto
	 * To get student with given user id from database.
	 */
	StudentDto getStudentByUserId(int userId);

	/**
	 * @since 0.0.1
	 * @param userDto
	 * @return boolean
	 * To updated student with given user id, return true if success, else false.
	 */
	boolean updateStudent(UserDto userDto);
}
