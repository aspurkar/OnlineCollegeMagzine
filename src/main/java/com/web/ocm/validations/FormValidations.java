package com.web.ocm.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.web.ocm.dto.ArticleDto;
import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.RegisterDto;
import com.web.ocm.dto.UserDto;

/**
 * @author Aditya
 * @since 0.0.1
 * 
 * Abstract class to perform form validations.
 */
public abstract class FormValidations {

	private static final String EMAIL_PATTERN = 
	        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean validateEmail(String emailStr) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailStr);
		 return matcher.matches();
	}
	
	public static boolean isNumber(String str)  
	{  
	  try  
	  {  
	    int i = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static List<String> validateLoginForm(LoginDto loginDto){
		List<String> errorMessages = new ArrayList<String>();
		if(loginDto == null || loginDto.getuName()==null || loginDto.getuName().equals("")){
			errorMessages.add("User name or email is required.");
		}
		if(loginDto == null || loginDto.getPwd()==null || loginDto.getPwd().equals("")){
			errorMessages.add("Password is required.");
		}
		return errorMessages;
	}
	
	public static List<String> validateEditUserForm(UserDto userDto){
		List<String> errorMsgs = new ArrayList<String>();
		if(userDto != null){
			if(userDto.getUname() == null || userDto.getUname().equals("")){
				errorMsgs.add("User Name is required.");
			}
			if(userDto.getFname() == null || userDto.getFname().equals("")){
				errorMsgs.add("First Name is required.");
			}
			if(userDto.getLname() == null || userDto.getLname().equals("")){
				errorMsgs.add("Last Name is required.");
			}
			if(userDto.getEmail() == null || userDto.getEmail().equals("")){
				errorMsgs.add("Email is required.");
			}else if(!validateEmail(userDto.getEmail())){
				errorMsgs.add("Invalid Email. (e.g., test@email.com)");
			}
			if(userDto.getAddress1() == null || userDto.getAddress1().equals("")){
				errorMsgs.add("Address is required.");
			}
			if(userDto.getCity() == null || userDto.getCity().equals("")){
				errorMsgs.add("City is required.");
			}
			if(userDto.getState() == null || userDto.getState().equals("")){
				errorMsgs.add("State is required.");
			}
			if(userDto.getCountry() == null || userDto.getCountry().equals("")){
				errorMsgs.add("Country is required.");
			}
			if(userDto.getPincode() == null || userDto.getPincode().equals("")){
				errorMsgs.add("Postal Code is required.");
			}else if(!isNumber(userDto.getPincode())){
				errorMsgs.add("Postal Code must be a number.");
			}
			if(userDto.getAboutme() == null || userDto.getAboutme().equals("")){
				errorMsgs.add("About me is required.");
			}
		}
		return errorMsgs;
	}

	public static List<String> validateStudentEditForm(UserDto userDto) {
		List<String> errorMsgs = new ArrayList<String>();

		if(userDto != null && userDto.getStudentDto() != null){
			errorMsgs = validateEditUserForm(userDto);
			if(userDto.getStudentDto().getRegNumber() == null || userDto.getStudentDto().getRegNumber().equals("")){
				errorMsgs.add("Registration Number is required.");
			}
			if(userDto.getStudentDto().getAlumni() == null || userDto.getStudentDto().getAlumni()==0){
				errorMsgs.add("Alumni is required.");
			}
			if(userDto.getStudentDto().getBranch() == null || userDto.getStudentDto().getBranch() .equals("")){
				errorMsgs.add("Branch is required.");
			}
			if(userDto.getStudentDto().getBatch() == null || userDto.getStudentDto().getBatch().equals("")){
				errorMsgs.add("Batch is required.");
			}
			if(userDto.getStudentDto().getProgram() == null || userDto.getStudentDto().getProgram().equals("")){
				errorMsgs.add("Program is required.");
			}
		}
		return errorMsgs;
	}
	
	public static List<String> validateSignUpForm(RegisterDto registerDto){
		List<String> errorMsgs = new ArrayList<String>();
		String address ="";
		
		if(registerDto != null){
			if(registerDto.getAddress1() !=null && !registerDto.getAddress1().isEmpty()){
				address = registerDto.getAddress1();
			}
			if(registerDto.getAddress2() !=null && !registerDto.getAddress2().isEmpty()){
				address = address+" "+registerDto.getAddress2();
			}
			if(registerDto.getAddress3() !=null && !registerDto.getAddress3().isEmpty()){
				address = address+" "+registerDto.getAddress3();
			}
			UserDto user = new UserDto();
			user.setAboutme(registerDto.getAboutme());
			user.setAddress1(address);
			user.setCity(registerDto.getCity());
			user.setState(registerDto.getState());
			user.setCountry(registerDto.getCountry());
			user.setPincode(String.valueOf(registerDto.getPincode()));
			user.setEmail(registerDto.getEmail());
			user.setUname(registerDto.getUname());
			user.setUserType(registerDto.getUserType());
			user.setPwd(registerDto.getPwd());
			user.setSquestion(registerDto.getSquestion());
			user.setSanswer(registerDto.getSanswer());
			user.setFname(registerDto.getFname());
			user.setLname(registerDto.getLname());
			user.setStudentDto(registerDto.getStudentDto());
			
			if (user.getUserType().equalsIgnoreCase("Student")) {
				errorMsgs = validateStudentEditForm(user);
			}else{
				errorMsgs = validateEditUserForm(user);
			}
			if(user.getUserType() == null || user.getUserType().equalsIgnoreCase("--Please Select--")){
				errorMsgs.add("User Type is required.");
			}
		}
		return errorMsgs;
	}
	
	public static List<String> validatePostArticleForm(ArticleDto artDto){
		List<String>errorMsgs = new ArrayList<String>();
		
		if(artDto != null){
			if(artDto.getHeading() == null || artDto.getHeading().equals("")){
				errorMsgs.add("Title is required.");
			}
			if(artDto.getArticleBody() == null || artDto.getArticleBody().equals("")){
				errorMsgs.add("Description is required.");
			}
		}
		
		return errorMsgs;
	}
}
