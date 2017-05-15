package com.web.ocm.services;

import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.RegisterDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Student;
import com.web.ocm.entities.User;

/**
 * @author Aditya
 * This interface declares method signatures to verify login credentials and to register new user.
 */
public interface LoginAndRegisterService {

	/**
	 * @param user
	 * @return UserDto
	 * This method verifies login credentials in DB and returns true if user found, else false.
	 */
	public UserDto verifyLogin(LoginDto loginDto);
	
	/**
	 * @param user
	 * @return boolean
	 * This method registers new user in DB and returns true if success, else false.
	 */
	public User registerNewUser(RegisterDto registerDto);
	
	/**
	 * @param user, registerDto
	 * @return Student
	 * This method registers new student in DB and returns true if success, else false.
	 */
	public Student saveStudent(User user,RegisterDto registerDto);
}
