package com.web.ocm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.services.AdminService;
import com.web.ocm.util.OcmPagesAndLinks;
import com.web.ocm.validations.FormValidations;

/**
 * @author Nikhita Busa
 *
 *	This class provides Adminstrator functionalities
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	 * @since 0.0.1
	 *
	 *	Autowire for {@link AdminService}
	 */
	@Autowired
	private AdminService adminService;
	
	/**
	 * @since 0.0.1
	 * @return String 
	 *	To get available users.
	 */
	@RequestMapping(value="/getUsers" , method = RequestMethod.GET)
	public String getUsersList(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< getUsersList() >>>>");
		
		List<UserDto> userDtos = adminService.getAllUsersList();
		map.put("users", userDtos);
		
		return OcmPagesAndLinks.ADMIN_MANAGE_USERS_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To get a given user.
	 */
	@RequestMapping(value="/manageUsers" , method = RequestMethod.GET)
	public String editUser(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< editUser() >>>>");
		String uid = request.getParameter("muserId");
		System.out.println("uid >>>> " + uid);
		
		UserDto userDto = adminService.getUserById(uid);
		model.addAttribute("profUser", userDto);
		map.put("mesg", "");
		map.put("errmesg", "");
		return OcmPagesAndLinks.ADMIN_EDIT_USER_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To update a given user.
	 */
	@RequestMapping(value="/updteUser" , method = RequestMethod.GET)
	public String updteUser(@ModelAttribute("profUser")UserDto userDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< updteUser() >>>>");
		List<String> errorMsgs = FormValidations.validateEditUserForm(userDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			model.addAttribute("profUser", userDto);
			map.put("errMsgs",errorMsgs);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.ADMIN_EDIT_USER_PAGE;
		}
		errorMsgs = new ArrayList<String>();
		UserDto user = null;
		try {
			user = (UserDto) request.getSession().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "Oops!! Unknown Exception occured. Please try again.");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
		
		if(user != null && user.getUserType().equalsIgnoreCase("Admin")){
			if(adminService.updateUser(userDto)){
				map.put("mesg", "Profile Successfully updated!");
				map.put("errmesg", "");
				return OcmPagesAndLinks.ADMIN_LANDING_PAGE;
			}else{
				map.put("mesg", "");
				map.put("errmesg", "Failed to update User!");
				model.addAttribute("profUser", userDto);
				return OcmPagesAndLinks.ADMIN_EDIT_USER_PAGE;
			}
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
		
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To remove a given user.
	 */
	@RequestMapping(value="/removeUser" , method = RequestMethod.GET)
	public String removeUser(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< editUser() >>>>");
		String uid = request.getParameter("muserId");
		System.out.println("uid >>>> " + uid);
		
		if(adminService.deleteUserById(uid)){
			map.put("mesg", "User Successfully removed!");
			map.put("errmesg", "");
			return OcmPagesAndLinks.ADMIN_LANDING_PAGE;
		}else{
			map.put("mesg", "");
			map.put("errmesg", "Failed to remove User.");
			return OcmPagesAndLinks.ADMIN_EDIT_USER_PAGE;
		}
		
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To get Admin profile page..
	 */
	@RequestMapping(value="/myProfile" , method = RequestMethod.GET)
	public String getAdminProfile(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< getAdminProfile() >>>>");
		UserDto user = null;
		try {
			user = (UserDto) request.getSession().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "Oops!! Unknown Exception occured. Please try again.");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
		if(user != null && user.getUserType().equalsIgnoreCase("Admin")){
			user = adminService.getUserById(String.valueOf(user.getUserId()));
			user.setStudentDto(null);
			map.put("profUser", user);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.ADMIN_PROFILE_PAGE;
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
}
