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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.web.ocm.dto.ArticleDto;
import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.ModeratorDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.services.AdminService;
import com.web.ocm.services.ArticleService;
import com.web.ocm.services.ModeratorService;
import com.web.ocm.util.OcmPagesAndLinks;
import com.web.ocm.validations.FormValidations;


@Controller
@RequestMapping(value="/moderator")
@SessionAttributes({"user", "login"})
public class ModeratorController {

	private static final Logger logger = LoggerFactory.getLogger(ModeratorController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ModeratorService moderatorService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value="/getModrProfile" , method = RequestMethod.GET)
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
			return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
		}
		if(user != null && user.getUserType().equalsIgnoreCase("Moderator")){
			user = adminService.getUserById(String.valueOf(user.getUserId()));
			//commented line is for future purpose
//			ModeratorDto dto =   moderatorService.getStudentByUserId(user.getUserId());
			user.setModerDto(new ModeratorDto());
			request.getSession().setAttribute("modrProf", user);
			model.addAttribute("modrProf",user);
			user.setStudentDto(null);
			map.put("modrProf", user);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.MODERATOR_PROFILE_PAGE;
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
	
	@RequestMapping(value="/updteModer" , method = RequestMethod.GET)
	public String updteModerator(@ModelAttribute("modrProf")UserDto userDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< updteModerator() >>>>");
		List<String> errorMsgs = FormValidations.validateEditUserForm(userDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			model.addAttribute("modrProf",userDto);
			userDto.setStudentDto(null);
			map.put("errMsgs", errorMsgs);
			map.put("modrProf", userDto);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.MODERATOR_PROFILE_PAGE;
		}
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
			return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
		}
		
		if(user != null && user.getUserType().equalsIgnoreCase("Moderator")){
			if(moderatorService.updateModerator(userDto)){
				map.put("mesg", "Profile Successfully updated!");
				map.put("errmesg", "");
				return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
			}else{
				map.put("mesg", "");
				map.put("errmesg", "Failed to update profile!");
				model.addAttribute("profUser", userDto);
				return OcmPagesAndLinks.MODERATOR_EDIT_PROFILE_PAGE;
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
	
	@RequestMapping(value="/getPostArticle" , method = RequestMethod.GET)
	public String getPostArticle(@ModelAttribute("article")ArticleDto articleDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
		Model model, HttpServletRequest request){
		logger.info("<<<< getMyProfile() >>>>");
		UserDto userDtos = (UserDto) request.getSession().getAttribute("user");
		map.put("article", new ArticleDto());
		model.addAttribute("user", userDtos);
		map.put("user", userDtos);
		map.put("mesg", "");
		map.put("errmesg", "");
		return OcmPagesAndLinks.MODERATOR_POST_ARTICLE_PAGE;
	}
	
	@RequestMapping(value="/savePostArticle" , method = RequestMethod.GET)
	public String savePostArticle(@ModelAttribute("article")ArticleDto articleDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
		Model model, HttpServletRequest request){
		logger.info("<<<< getMyProfile() >>>>");
		UserDto userDtos = (UserDto) request.getSession().getAttribute("user");
		List<String> errorMsgs = FormValidations.validatePostArticleForm(articleDto);
		if(errorMsgs != null && errorMsgs.size()>0){
			model.addAttribute("article", articleDto);
			model.addAttribute("user", userDtos);
			
			map.put("errMsgs",errorMsgs);
			map.put("article", articleDto);
			map.put("user", userDtos);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.MODERATOR_POST_ARTICLE_PAGE;
		}
		map.put("errMsgs",new ArrayList<String>());
		if(articleService.saveArticle(articleDto,userDtos.getUserId())){
			map.put("mesg", "Article Successfully poseted!");
			map.put("errmesg", "");
		}else{
			map.put("mesg", "");
			map.put("errmesg", "Failed to post Article!");
			model.addAttribute("user", userDtos);
			model.addAttribute("student", userDtos.getStudentDto());
		}
		return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
	}
}
