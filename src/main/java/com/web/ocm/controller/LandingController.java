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

import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.ModeratorDto;
import com.web.ocm.dto.RegisterDto;
import com.web.ocm.dto.StudentDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Article;
import com.web.ocm.entities.Student;
import com.web.ocm.entities.User;
import com.web.ocm.services.ArticleService;
import com.web.ocm.services.LoginAndRegisterService;
import com.web.ocm.util.OcmPagesAndLinks;
import com.web.ocm.validations.FormValidations;

/**
 * @author Nikhita Busa
 *
 * Controller class to handle login and after login operations.
 */

@Controller
@SessionAttributes({"user", "login"})
public class LandingController {

	private static final Logger logger = LoggerFactory.getLogger(LandingController.class);
	
	@Autowired
	private LoginAndRegisterService loginService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return login page.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("<<<< Inside index() >>>>");
		
		LoginDto loginDto = new LoginDto();
		model.addAttribute("login", loginDto );
		
		return OcmPagesAndLinks.LANDING_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return respective user landing language based on user type.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request) {
		logger.info("<<<< Inside login() >>>>");
		
		List<String> errorMsgs = FormValidations.validateLoginForm(loginDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			map.put("errmsgs", errorMsgs);
			loginDto = new LoginDto();
			model.addAttribute("login", loginDto );
			return OcmPagesAndLinks.LANDING_PAGE;
		}
		map.put("errmsgs", new ArrayList<String>());
		UserDto userDto = new UserDto();
		
		userDto = loginService.verifyLogin(loginDto);
		if(userDto == null){
			errorMsgs = new ArrayList<String>();
			errorMsgs.add("Oops!! This User doesn't exist.");
			map.put("errmsgs", errorMsgs);
			loginDto = new LoginDto();
			model.addAttribute("login", loginDto );
			return OcmPagesAndLinks.LANDING_PAGE;
		}
		List<Article> articles = articleService.getArticles();
		userDto.setArticleDtoList(articles);
		if(userDto != null && userDto.getUserType().equalsIgnoreCase("Admin")){
			model.addAttribute("user", userDto);
			request.getSession().setAttribute("user", userDto);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.ADMIN_LANDING_PAGE;
		} else if(userDto.getUserType().equalsIgnoreCase("Moderator")){
			model.addAttribute("user", userDto);
			model.addAttribute("moder", new ModeratorDto());
			request.getSession().setAttribute("user", userDto);
			map.put("userId", userDto.getUserId());
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
		} else if(userDto.getUserType().equalsIgnoreCase("Student")){
			model.addAttribute("user", userDto);
			model.addAttribute("student", new StudentDto());
			request.getSession().setAttribute("user", userDto);
			map.put("userId", userDto.getUserId());
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.STUDENT_LANDING_PAGE;
		} else{
			map.put("error", "Invalid Credentials! Please try again.");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return dashboard of respective user types.
	 */
	@RequestMapping(value = "/getDashboard", method = RequestMethod.GET)
	public String getAdminDashboard(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request) {
		logger.info("<<<< Inside login() >>>>");
		
		if(loginDto == null){
			loginDto = new LoginDto();
			model.addAttribute("login", loginDto );
			
			return OcmPagesAndLinks.LANDING_PAGE;
		}else if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			List<Article> articles = articleService.getArticles();
			userDto.setArticleDtoList(articles);
			model.addAttribute("user", userDto);
			map.put("mesg", "");
			map.put("errmesg", "");
			if(userDto != null && userDto.getUserType().equalsIgnoreCase("Admin")){
				return OcmPagesAndLinks.ADMIN_LANDING_PAGE;
			}else if(userDto != null && userDto.getUserType().equalsIgnoreCase("Moderator")){
				return OcmPagesAndLinks.MODERATOR_LANDING_PAGE;
			}else if(userDto != null && userDto.getUserType().equalsIgnoreCase("Student")){
				return OcmPagesAndLinks.STUDENT_LANDING_PAGE;
			}else{
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				loginDto = new LoginDto();
				model.addAttribute("login", loginDto );
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "Please login!!");
				return OcmPagesAndLinks.LANDING_PAGE;
			}
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			loginDto = new LoginDto();
			model.addAttribute("login", loginDto );
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "Please login!!");
			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return Signup page.
	 */
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUp(Model model) {
		logger.info("<<<< Inside signUp() >>>>");
		
		RegisterDto registerDto = new RegisterDto();
		
		List<String> userTypes = new ArrayList<String>();
		userTypes.add(0,"--Please Select--");
		userTypes.add(1,"Student");
		userTypes.add(2,"Moderator");
		userTypes.add(3,"Admin");
		
		registerDto.setUserTypeList(userTypes);
		
		model.addAttribute("register", registerDto );
		
		return OcmPagesAndLinks.SIGNUP_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To register new user.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(@ModelAttribute("register")RegisterDto registerDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request) {
		logger.info("<<<< Inside register() >>>>");
		
		List<String> errorMsgs = FormValidations.validateSignUpForm(registerDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			List<String> userTypes = new ArrayList<String>();
			userTypes.add(0,"--Please Select--");
			userTypes.add(1,"Student");
			userTypes.add(2,"Moderator");
			userTypes.add(3,"Admin");
			
			registerDto.setUserTypeList(userTypes);
			
			model.addAttribute("register", registerDto );
			map.put("errmsgs", errorMsgs);
			return OcmPagesAndLinks.SIGNUP_PAGE;
		}
		User user = loginService.registerNewUser(registerDto);
		if(user != null && user.getUserId() == 0){
			List<String> userTypes = new ArrayList<String>();
			userTypes.add(0,"--Please Select--");
			userTypes.add(1,"Student");
			userTypes.add(2,"Moderator");
			userTypes.add(3,"Admin");
			
			registerDto.setUserTypeList(userTypes);
			
			errorMsgs = new ArrayList<String>();
			errorMsgs.add("User name already in use.");
			model.addAttribute("register", registerDto );
			map.put("register", registerDto);
			map.put("errmsgs", errorMsgs);
			return OcmPagesAndLinks.SIGNUP_PAGE;
		}
		if(user == null){
			errorMsgs = new ArrayList<String>();
			errorMsgs.add("Oops!! Unknown Exception occured. Please try again.");
			model.addAttribute("register", registerDto );
			map.put("errmsgs", errorMsgs);
			return OcmPagesAndLinks.SIGNUP_PAGE;
		}
		if (registerDto.getUserType().equalsIgnoreCase("Student")) {
			logger.info("<<<< Student saved >>>>" + user.getUserId());
			Student student = loginService.saveStudent(user, registerDto);
			logger.info("<<<< Student saved >>>>" + student.getStudentId());
		}
		errorMsgs = new ArrayList<String>();
		errorMsgs.add("Your registration is successful. You can login now.");
		map.put("errmsgs", new ArrayList<String>());
		map.put("msgs", errorMsgs);
		LoginDto loginDto = new LoginDto();
		model.addAttribute("login", loginDto );
		return OcmPagesAndLinks.LANDING_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To logout user and return login page.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (@ModelAttribute("user")UserDto user,SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request) {
			
		if(request.getSession() != null && request.getSession().getAttribute("user") != null){
			request.getSession().removeAttribute("user");
		}
		if(request.getSession() != null && request.getSession().getAttribute("login") != null){
			request.getSession().removeAttribute("login");
		}
		if(request.getSession() != null && request.getSession().getAttribute("modrProf") != null){
			request.getSession().removeAttribute("modrProf");
		}
		if(request.getSession() != null && request.getSession().getAttribute("searchArt") != null){
			request.getSession().removeAttribute("searchArt");
		}
		request.getSession().invalidate();
		status.setComplete();
		
		
	    return "redirect:/";
	}
}
