/**
 * 
 */
package com.web.ocm.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ocm.controller.ModeratorController;
import com.web.ocm.dto.ModeratorDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.User;
import com.web.ocm.repo.ModeratorRepo;
import com.web.ocm.repo.UserRepo;
import com.web.ocm.services.ModeratorService;

@Service("moderatorService")
public class ModeratorServiceImpl implements ModeratorService {


	@Autowired
	private UserRepo userRepo;

	/* (non-Javadoc)
	 * @see com.web.ocm.services.ModeratorService#getStudentByUserId(int)
	 */
	@Override
	public ModeratorDto getModeratorByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean updateModerator(UserDto userDto) {

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

}
