package com.web.ocm.controller;

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

import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.SearchArticlesDto;
import com.web.ocm.dto.StudentDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Article;
import com.web.ocm.services.AdminService;
import com.web.ocm.services.ArticleService;
import com.web.ocm.util.OcmPagesAndLinks;

/**
 * @author Nikhita Busa
 *
 * Controller class for Articles management.
 */
@Controller
@RequestMapping(value="/article")
public class ArticleController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	/**
	 * @since 0.0.1
	 *
	 *	Autowire for {@link AdminService}
	 */
	@Autowired
	private ArticleService articleService;

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To get available articles.
	 */
	@RequestMapping(value="/viewArticle" , method = RequestMethod.GET)
	public String getArticlesList(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< getArticlesList() >>>>");
		String returnPage = OcmPagesAndLinks.LANDING_PAGE;

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");

			String artId = request.getParameter("artId");

			Article art = articleService.getArticleById(artId);

			model.addAttribute("article", art);

			if(user != null && user.getUserType().equalsIgnoreCase("Admin")){
				returnPage = OcmPagesAndLinks.VIEW_ARTICLE_PAGE;
			}else if(user != null && user.getUserType().equalsIgnoreCase("Moderator")){
				returnPage = OcmPagesAndLinks.MODERATOR_VIEW_ARTICLE_PAGE;
			}else{
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "You are kicked out, for unauthorized access!!");
			}
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
		}
		return returnPage;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return to login page.
	 */
	public String sendToLanding(HttpServletRequest request, SessionStatus status, Map<String, Object> map){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("login");
		status.setComplete();
		map.put("mesg", "");
		map.put("errmesg", "");
		return  OcmPagesAndLinks.LANDING_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return manage articles page.
	 */
	@RequestMapping(value="/manageArticles" , method = RequestMethod.GET)
	public String manageArticles(@ModelAttribute("login")LoginDto loginDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< manageArticles() >>>>");
		String returnPage = OcmPagesAndLinks.LANDING_PAGE;

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && (user.getUserType().equalsIgnoreCase("Admin") || user.getUserType().equalsIgnoreCase("Moderator"))){
				SearchArticlesDto searchArtDto = new SearchArticlesDto();

				searchArtDto.setCategoryList(articleService.getCategoryList());

				model.addAttribute("searchArt", searchArtDto);
				if(user.getUserType().equalsIgnoreCase("Admin")){
					returnPage = OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
				}else if(user.getUserType().equalsIgnoreCase("Moderator")){
					returnPage = OcmPagesAndLinks.MODERATOR_MANAGE_ARTICLES_PAGE;
				}else{
					returnPage = sendToLanding(request,status,map);
				}

			}else{
				returnPage = sendToLanding(request,status,map);
			}
		}else{
			loginDto = new LoginDto();
			model.addAttribute("login", loginDto );
			returnPage = sendToLanding(request,status,map);
		}
		return returnPage;
	}

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return search articles page.
	 */
	@RequestMapping(value="/search")
	public String searchArticles(@ModelAttribute("searchArt")SearchArticlesDto searchArtDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request, HttpSession session) {

		model.addAttribute("searchArt", searchArtDto);
		request.getSession().setAttribute("searchArt", searchArtDto);
		return OcmPagesAndLinks.SEARCH_ARTICLES_PAGE;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return result articles page.
	 */
	@RequestMapping(value="/performSearch")
	public String performSearch(@ModelAttribute("searchArt")SearchArticlesDto searchArtDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request, HttpSession session) {

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && (user.getUserType().equalsIgnoreCase("Admin") || user.getUserType().equalsIgnoreCase("Moderator"))){
				List<Article> resultArticles = articleService.searchArticles(searchArtDto);

				if(resultArticles == null || resultArticles.size() <=0){
					map.put("mesg", "Oops!! No Articles found.");
					map.put("errmesg", "");
					searchArtDto = new SearchArticlesDto();

					searchArtDto.setCategoryList(articleService.getCategoryList());

					model.addAttribute("searchArt", searchArtDto);
					return OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
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
				if(user.getUserType().equalsIgnoreCase("Admin")){
					return OcmPagesAndLinks.SEARCH_ARTICLES_PAGE;
				}else if(user.getUserType().equalsIgnoreCase("Moderator")){
					return OcmPagesAndLinks.MODERATOR_SEARCH_ARTICLES_PAGE;
				}
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "You are kicked out, for unauthorized access!!");
				return OcmPagesAndLinks.LANDING_PAGE;
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
			model.addAttribute("login", loginDto);

			return OcmPagesAndLinks.LANDING_PAGE;
		}

	}

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return next articles result page.
	 */
	@RequestMapping(value="/postSearch", method = RequestMethod.GET)
	public String displayPostSearchPage(@RequestParam("pageId")String pageNumber, Model model ,@ModelAttribute("searchArt")SearchArticlesDto searchArtDto,
			SessionStatus status,Map<String, Object> map,HttpServletRequest request ,HttpSession session) {	

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			if(user != null && (user.getUserType().equalsIgnoreCase("Admin") || user.getUserType().equalsIgnoreCase("Moderator"))){
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
				if(user.getUserType().equalsIgnoreCase("Admin")){
					return OcmPagesAndLinks.POST_SEARCH_ARTICLE_PAGE;
				}else if(user.getUserType().equalsIgnoreCase("Moderator")){
					return OcmPagesAndLinks.MODERATOR_POST_SEARCH_ARTICLE_PAGE;
				}
				request.getSession().removeAttribute("user");
				request.getSession().removeAttribute("login");
				status.setComplete();
				map.put("mesg", "");
				map.put("errmesg", "You are kicked out, for unauthorized access!!");
				return OcmPagesAndLinks.LANDING_PAGE;
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
			model.addAttribute("login", loginDto);

			return OcmPagesAndLinks.LANDING_PAGE;
		}

	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To approve given article page.
	 */
	@RequestMapping(value="/approveArticle" , method = RequestMethod.GET)
	public String approveArticle(@ModelAttribute("searchArt")SearchArticlesDto searchArtDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< approveArticle() >>>>");
		if(request != null && request.getSession().getAttribute("user") != null){
			String artId = request.getParameter("artId");
			Article artDto = articleService.getArticleById(artId);
			if(artDto.getPostStatus() == 0 && articleService.approveArticle(artId)){
				List<Article> resultArticles = articleService.searchArticles(searchArtDto);
				model.addAttribute("articles", resultArticles);

				map.put("mesg", "Article approved successfully.");
				map.put("errmesg", "");

				//				return OcmPagesAndLinks.SEARCH_ARTICLES_PAGE;

				if(resultArticles == null || resultArticles.size() <=0){
					map.put("mesg", "Oops!! No Articles found.");
					map.put("errmesg", "");
					return null;
				}
				int noOfPages = 1;
				int size = articleService.countArticles();
				try {
					noOfPages = (int)size/3;
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("noOfPages >>>> " + noOfPages);
				map.put("result", resultArticles);
				map.put("currentIndex", "1");
				map.put("endIndex",String.valueOf(noOfPages));
				model.addAttribute("searchArt", searchArtDto);
				request.getSession().setAttribute("searchArt", searchArtDto);
				return OcmPagesAndLinks.SEARCH_ARTICLES_PAGE;
			}else{
				map.put("mesg", "");
				map.put("errmesg", "Failed to approved article!");
				searchArtDto = new SearchArticlesDto();
				model.addAttribute("searchArt", searchArtDto);
				return OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
			}
		}else{
			LoginDto loginDto = new LoginDto();
			model.addAttribute("login", loginDto );

			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To return edit articles page.
	 */
	@RequestMapping(value="/editArticle" , method = RequestMethod.GET)
	public String editArticle(@ModelAttribute("searchArt")SearchArticlesDto searchArtDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< editArticle() >>>>");
		String returnPage = OcmPagesAndLinks.LANDING_PAGE;

		if(request != null && request.getSession() != null && request.getSession().getAttribute("user") != null){
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			String artId = request.getParameter("artId");
			Article artDto = articleService.getArticleById(artId);
			model.addAttribute("article", artDto);
			if(user != null && user.getUserType().equalsIgnoreCase("Admin")){
				returnPage = OcmPagesAndLinks.EDIT_ARTICLE_PAGE;
			}else if(user != null && user.getUserType().equalsIgnoreCase("Moderator")){
				returnPage = OcmPagesAndLinks.MODERATOR_EDIT_ARTICLE_PAGE;
			}
		}else{
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("login");
			status.setComplete();
			map.put("mesg", "");
			map.put("errmesg", "You are kicked out, for unauthorized access!!");
		}
		return returnPage;
	}
	
	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To update given article.
	 */
	@RequestMapping(value="/updateArticle" , method = RequestMethod.GET)
	public String updateArticle(@ModelAttribute("article")Article artDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< editArticle() >>>>");

		String returnPage = OcmPagesAndLinks.LANDING_PAGE;

		int userId;
		String artId = request.getParameter("artId");
		String catId = request.getParameter("catId");

		artDto.setArticleId(Integer.parseInt(artId));

		if(request != null && request.getSession().getAttribute("user") != null){
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			userId = userDto.getUserId();

			if(articleService.updateArticle(artDto, userId, catId)){
				map.put("mesg", "Article updated successfully.");
				map.put("errmesg", "");
				SearchArticlesDto searchArtDto = new SearchArticlesDto();
				searchArtDto.setCategoryList(articleService.getCategoryList());

				model.addAttribute("searchArt", searchArtDto);
			}else{
				map.put("mesg", "");
				map.put("errmesg", "Failed to update Article!");
				SearchArticlesDto searchArtDto = new SearchArticlesDto();
				model.addAttribute("searchArt", searchArtDto);
			}
			if(userDto != null && userDto.getUserType().equalsIgnoreCase("Admin")){
				returnPage = OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
			}else if(userDto != null && userDto.getUserType().equalsIgnoreCase("Moderator")){
				returnPage = OcmPagesAndLinks.MODERATOR_MANAGE_ARTICLES_PAGE;
			}
			return returnPage;
		}else{
			LoginDto loginDto = new LoginDto();
			model.addAttribute("login", loginDto );

			return returnPage;
		}
	}

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To remove given article.
	 */
	@RequestMapping(value="/rmvArticle" , method = RequestMethod.GET)
	public String removeArticleFromDashboard(@ModelAttribute("article")Article artDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		if(request != null && request.getSession().getAttribute("user") != null){
			String artId = request.getParameter("artId");
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			if(userDto.getUserType().equalsIgnoreCase("Admin")){
				if(articleService.removeArticle(artId)){
					map.put("mesg", "Article removed successfully.");
					map.put("errmesg", "");

					List<Article> articles = articleService.getArticles();
					userDto.setArticleDtoList(articles);
					model.addAttribute("user", userDto);
					request.getSession().setAttribute("user", userDto);
					return OcmPagesAndLinks.ADMIN_LANDING_PAGE;

				}else{
					map.put("mesg", "");
					map.put("errmesg", "Failed to remove Article!");
					SearchArticlesDto searchArtDto = new SearchArticlesDto();
					model.addAttribute("searchArt", searchArtDto);
					return OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
				}	
			}
		}
		return "redirect:/logut";
	}

	/**
	 * @since 0.0.1
	 * @return String 
	 * 
	 * To remove searched article.
	 */
	@RequestMapping(value="/removeArticle" , method = RequestMethod.GET)
	public String removeArticle(@ModelAttribute("article")Article artDto, SessionStatus status, BindingResult result, Map<String, Object> map, Locale locale,
			Model model, HttpServletRequest request){
		logger.info("<<<< approveArticle() >>>>");

		String artId = request.getParameter("artId");

		if(request != null && request.getSession().getAttribute("user") != null){
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			if(userDto.getUserType().equalsIgnoreCase("Admin")){
				if(articleService.removeArticle(artId)){
					map.put("mesg", "Article removed successfully.");
					map.put("errmesg", "");

					SearchArticlesDto searchArtDto = new SearchArticlesDto();
					searchArtDto = (SearchArticlesDto)request.getSession().getAttribute("searchArt");
					List<Article> resultArticles = articleService.searchArticles(searchArtDto);

					if(resultArticles == null || resultArticles.size() <=0){
						map.put("mesg", "Oops!! No Articles found.");
						map.put("errmesg", "");
						return null;
					}
					int noOfPages = 1;
					int size = articleService.countArticles();
					try {
						noOfPages = (int)size/3;
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("noOfPages >>>> " + noOfPages);
					map.put("result", resultArticles);
					map.put("currentIndex", "1");
					map.put("endIndex",String.valueOf(noOfPages));
					model.addAttribute("searchArt", searchArtDto);
					request.getSession().setAttribute("searchArt", searchArtDto);
					return OcmPagesAndLinks.SEARCH_ARTICLES_PAGE;
				}else{
					map.put("mesg", "");
					map.put("errmesg", "Failed to remove Article!");
					SearchArticlesDto searchArtDto = new SearchArticlesDto();
					model.addAttribute("searchArt", searchArtDto);
					return OcmPagesAndLinks.MANAGE_ARTICLES_PAGE;
				}	
			}else if(userDto.getUserType().equalsIgnoreCase("Student")){
				map.put("mesg", "");
				map.put("errmesg", "Oops! You are not authorized to perform this operation.");
				model.addAttribute("user", userDto);
				model.addAttribute("student", new StudentDto());
				map.put("userId", userDto.getUserId());
				return OcmPagesAndLinks.STUDENT_LANDING_PAGE;
			}else{
				LoginDto loginDto = new LoginDto();
				model.addAttribute("login", loginDto );

				return OcmPagesAndLinks.LANDING_PAGE;
			}
		}else{
			LoginDto loginDto = new LoginDto();
			model.addAttribute("login", loginDto );

			return OcmPagesAndLinks.LANDING_PAGE;
		}
	}
}
