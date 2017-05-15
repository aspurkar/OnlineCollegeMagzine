package com.web.ocm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.web.ocm.dto.ArticleDto;
import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.SearchArticlesDto;
import com.web.ocm.dto.StudentDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Article;
import com.web.ocm.services.ArticleService;
import com.web.ocm.services.StudentService;
import com.web.ocm.util.OcmPagesAndLinks;
import com.web.ocm.validations.FormValidations;


@Controller
@RequestMapping(value="/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ArticleService articleService;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@RequestMapping(value="/getMyProfile" , method = RequestMethod.GET)
	public String getUsersList(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
		Model model, HttpServletRequest request){
		logger.info("<<<< getMyProfile() >>>>");
//		String userId  = (String)request.getParameter("studentId");
		UserDto userDtos = (UserDto) request.getSession().getAttribute("user");
		StudentDto dto =   studentService.getStudentByUserId(userDtos.getUserId());
		userDtos.setStudentDto(dto);
		map.put("profUser", userDtos);
		map.put("mesg", "");
		map.put("errmesg", "");
		return OcmPagesAndLinks.STUDENT_PROFILE_PAGE;
	}
	

	@RequestMapping(value="/updteStudent" , method = RequestMethod.GET)
	public String updteUser(@ModelAttribute("profUser")UserDto userDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< updteUser() >>>>");
		List<String> errorMsgs = FormValidations.validateStudentEditForm(userDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			map.put("errMsgs", errorMsgs);
			map.put("profUser", userDto);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.STUDENT_PROFILE_PAGE;
		}
		if(studentService.updateStudent(userDto)){
			map.put("mesg", "Profile Successfully updated!");
			map.put("errmesg", "");
			map.put("userId", userDto.getUserId());
			model.addAttribute("user", userDto);
			model.addAttribute("student", userDto.getStudentDto());
			return OcmPagesAndLinks.STUDENT_LANDING_PAGE;
		}else{
			map.put("mesg", "");
			map.put("errmesg", "Failed to update profile!");
			model.addAttribute("profUser", userDto);
			return OcmPagesAndLinks.STUDENT_EDIT_PROFILE_PAGE;
		}
	}
	
	@RequestMapping(value="/getPostArticle" , method = RequestMethod.GET)
	public String getPostArticle(@ModelAttribute("article")ArticleDto articleDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
		Model model, HttpServletRequest request){
		logger.info("<<<< getMyProfile() >>>>");
		UserDto userDtos = (UserDto) request.getSession().getAttribute("user");
		map.put("article", new ArticleDto());
		map.put("user", userDtos);
		map.put("mesg", "");
		map.put("errmesg", "");
		return OcmPagesAndLinks.STUDENT_ARTICLE_PAGE;
	}
	
	@RequestMapping(value="/savePostArticle" , method = RequestMethod.GET)
	public String savePostArticle(@ModelAttribute("article")ArticleDto articleDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
		Model model, HttpServletRequest request){
		logger.info("<<<< getMyProfile() >>>>");
		UserDto userDtos = (UserDto) request.getSession().getAttribute("user");
		List<String>errorMsgs = FormValidations.validatePostArticleForm(articleDto);
		if(errorMsgs != null && errorMsgs.size() > 0){
			map.put("article", new ArticleDto());
			map.put("user", userDtos);
			map.put("errmsgs", errorMsgs);
			map.put("mesg", "");
			map.put("errmesg", "");
			return OcmPagesAndLinks.STUDENT_ARTICLE_PAGE;
		}
		map.put("errMsgs",new ArrayList<String>());
		if(articleService.saveArticle(articleDto,userDtos.getUserId())){
			map.put("mesg", "Article Successfully poseted!");
			map.put("errmesg", "");
			map.put("userId", userDtos.getUserId());
			model.addAttribute("user", userDtos);
			model.addAttribute("student", userDtos.getStudentDto());
		}else{
			map.put("article", new ArticleDto());
			map.put("mesg", "");
			map.put("errmesg", "Failed to post Article!");
			map.put("errmesg", "");
			map.put("userId", userDtos.getUserId());
			model.addAttribute("user", userDtos);
			model.addAttribute("student", userDtos.getStudentDto());
		}
		return OcmPagesAndLinks.STUDENT_LANDING_PAGE;
	}
	
	@RequestMapping(value="/findArticles" , method = RequestMethod.GET)
	public String findArticles(@ModelAttribute("profUser")UserDto userDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< manageArticles() >>>>");
		String returnPage = OcmPagesAndLinks.STUDENT_LANDING_PAGE;

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && user.getUserType().equalsIgnoreCase("Student")){
				SearchArticlesDto searchArtDto = new SearchArticlesDto();

				searchArtDto.setCategoryList(articleService.getCategoryList());

				model.addAttribute("searchArt", searchArtDto);
				returnPage = OcmPagesAndLinks.STUDENT_FIND_ARTICLES_PAGE;

			}else{
				map.put("mesg", "");
				map.put("errmesg", "You are not allowd to perform this operation!");
				map.put("userId", userDto.getUserId());
				model.addAttribute("user", userDto);
				model.addAttribute("student", userDto.getStudentDto());
			}
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "");
			returnPage = OcmPagesAndLinks.LANDING_PAGE;
		}
		return returnPage;
	}
	
	@RequestMapping(value="/performArtSearch")
	public String performSearch(@ModelAttribute("searchArt")SearchArticlesDto searchArtDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request, HttpSession session) {

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && user.getUserType().equalsIgnoreCase("Student")){
				List<Article> resultArticles = articleService.searchArticles(searchArtDto);

				if(resultArticles == null || resultArticles.size() <=0){
					map.put("mesg", "Oops!! No Articles found.");
					map.put("errmesg", "");
					searchArtDto = new SearchArticlesDto();

					searchArtDto.setCategoryList(articleService.getCategoryList());

					model.addAttribute("searchArt", searchArtDto);
					return OcmPagesAndLinks.STUDENT_FIND_ARTICLES_PAGE;
				}
				int noOfPages = 1;
				int size = articleService.countArticles();
				//				int size = resultArticles.size();
				try {
					if(size > 3 ){
						noOfPages = (int)size/3;
						if(size % 3 != 0){
							noOfPages++;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("noOfPages >>>> " + noOfPages);
				map.put("result", resultArticles);
				map.put("currentIndex", "1");
				map.put("endIndex",String.valueOf(noOfPages));
				model.addAttribute("searchArt", searchArtDto);
				request.getSession().setAttribute("searchArt", searchArtDto);
				return OcmPagesAndLinks.STUDENT_SEARCH_ARTICLES_PAGE;
			}else{
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "You are not allowed to perform this operation!");
				return OcmPagesAndLinks.LANDING_PAGE;
			}

		}else{
			LoginDto loginDto = new LoginDto();
			model.addAttribute("login", loginDto);

			return OcmPagesAndLinks.LANDING_PAGE;
		}

	}
	
	@RequestMapping(value="/postSearchArt", method = RequestMethod.GET)
	public String displayPostSearchPage(@RequestParam("pageId")String pageNumber, Model model ,@ModelAttribute("searchArt")SearchArticlesDto searchArtDto,
			SessionStatus status,Map<String, Object> map,HttpServletRequest request ,HttpSession session) {	

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && user.getUserType().equalsIgnoreCase("Student")){
				if(request != null && request.getSession() != null && request.getSession().getAttribute("searchArt") != null){
					searchArtDto = (SearchArticlesDto) request.getSession().getAttribute("searchArt");
				}
				Page<Article> page = articleService.postSearchArticles(searchArtDto, Integer.parseInt(pageNumber));
				logger.info("pageNumber >>>> " + pageNumber);
				int begin ;
				int current = page.getNumber() + 1;

				if((page.getTotalPages())-3 < current ){			
					begin = (page.getTotalPages())-3;
				}else{			
					begin = Math.max(current, current - 2);
				}	
				int end = Math.min(begin + 3, page.getTotalPages());
				searchArtDto.setCurrentIndex(current);
				map.put("page", page);
				map.put("beginIndex", begin);
				map.put("endIndex", end);
				map.put("currentIndex", current);
				session.setAttribute("page", page);
				request.getSession().setAttribute("searchArt", searchArtDto);
				logger.info("current >>>> " + current);
				
				return OcmPagesAndLinks.STUDENT_POST_SEARCH_ARTICLE_PAGE;
			}else{
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "You are kicked out, for unauthorized access!!");
				return OcmPagesAndLinks.LANDING_PAGE;
			}
		}else{
			LoginDto loginDto = new LoginDto();
			map.put("errmsgs", "You are not authorized. Please login and try.");
			model.addAttribute("login", loginDto);

			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
	
	@RequestMapping(value="/viewArticle" , method = RequestMethod.GET)
	public String viewArticle(@ModelAttribute("profUser")UserDto userDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< viewArticle() >>>>");
		String returnPage = OcmPagesAndLinks.LANDING_PAGE;

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");

			String artId = request.getParameter("artId");

			Article art = articleService.getArticleById(artId);

			model.addAttribute("article", art);

			returnPage = OcmPagesAndLinks.STUDENT_VIEW_ARTICLE_PAGE;
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
		}
		return returnPage;
	}
}
