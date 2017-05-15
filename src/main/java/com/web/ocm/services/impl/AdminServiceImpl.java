/**
 * 
 */
package com.web.ocm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.User;
import com.web.ocm.repo.UserRepo;
import com.web.ocm.services.AdminService;


@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepo userRepo;
	
	/* (non-Javadoc)
	 * @see com.web.ocm.services.AdminService#getAllUsersList()
	 */
	@Override
	public List<UserDto> getAllUsersList() {
		List<User> users = userRepo.findAll();
		
		List<UserDto> usersList = new ArrayList<UserDto>();
		if(users != null && users.size()>0){

			for(User user: users){
				UserDto userDto = new UserDto();
				userDto.setAboutme(user.getAboutMe());
				userDto.setAddress1(user.getAddress());
				userDto.setCity(user.getCity());
				userDto.setCountry(user.getCountry());
				userDto.setEmail(user.getEmail());
				userDto.setFname(user.getNameFirst());
				userDto.setLname(user.getNameLast());
				userDto.setPincode(String.valueOf(user.getPinCode()));
				userDto.setSanswer(user.getSecurityAnswer());
				userDto.setSquestion(user.getSecurityQuestion());
				userDto.setUname(user.getUserName());
				userDto.setUserId(user.getUserId());
				userDto.setUserType(user.getType());
				
				usersList.add(userDto);
			}
			
			return usersList;
		}else{
			return new ArrayList<UserDto>();
		}
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.AdminService#getUserById()
	 */
	@Override
	public UserDto getUserById(String userId){
		User user = null;
		try {
			user = userRepo.findByUserId(Integer.parseInt(userId));
			if(user != null && user.getUserId() == Long.parseLong(userId)){
				UserDto userDto = new UserDto();
				userDto.setAboutme(user.getAboutMe());
				userDto.setAddress1(user.getAddress());
				userDto.setCity(user.getCity());
				userDto.setState(user.getState());
				userDto.setCountry(user.getCountry());
				userDto.setEmail(user.getEmail());
				userDto.setFname(user.getNameFirst());
				userDto.setLname(user.getNameLast());
				userDto.setPincode(String.valueOf(user.getPinCode()));
				userDto.setSanswer(user.getSecurityAnswer());
				userDto.setSquestion(user.getSecurityQuestion());
				userDto.setUname(user.getUserName());
				userDto.setUserId(user.getUserId());
				userDto.setUserType(user.getType());
				
				return userDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.AdminService#updateUser()
	 */
	@Override
	@Transactional
	public boolean updateUser(UserDto userDto) {
		try {
			User user = userRepo.findByUserId(userDto.getUserId());
			
			user.setUserName(userDto.getUname());
			user.setAboutMe(userDto.getAboutme());
			user.setAddress(userDto.getAddress1());
			user.setCity(userDto.getCity());
			user.setCountry(userDto.getCountry());
			user.setState(userDto.getState());
			user.setPinCode(Integer.parseInt(userDto.getPincode()));
			user.setEmail(userDto.getEmail());
			user.setNameFirst(userDto.getFname());
			user.setNameLast(userDto.getLname());
			
			userRepo.saveAndFlush(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.AdminService#deleteUserById()
	 */
	@Override
	@Transactional
	public boolean deleteUserById(String uid) {
		try {
			User user = userRepo.findByUserId(Integer.parseInt(uid));
			
			userRepo.delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
