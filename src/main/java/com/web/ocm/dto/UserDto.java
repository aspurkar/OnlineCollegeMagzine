package com.web.ocm.dto;

import java.io.Serializable;
import java.util.List;

import com.web.ocm.entities.Article;
import com.web.ocm.entities.User;


public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private int userId;
	
	private String pincode;
	
	private String fname;
	private String lname;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private String country;
	private String email;
	private String uname;
	private String pwd;
	private String repwd;
	private String squestion;
	private String sanswer;
	private String aboutme;
	private String userType;
	
	private List<Article> articleDtoList;
	
	private StudentDto studentDto;
	private ModeratorDto moderDto;
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}
	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the repwd
	 */
	public String getRepwd() {
		return repwd;
	}
	/**
	 * @param repwd the repwd to set
	 */
	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}
	/**
	 * @return the squestion
	 */
	public String getSquestion() {
		return squestion;
	}
	/**
	 * @param squestion the squestion to set
	 */
	public void setSquestion(String squestion) {
		this.squestion = squestion;
	}
	/**
	 * @return the sanswer
	 */
	public String getSanswer() {
		return sanswer;
	}
	/**
	 * @param sanswer the sanswer to set
	 */
	public void setSanswer(String sanswer) {
		this.sanswer = sanswer;
	}
	/**
	 * @return the aboutme
	 */
	public String getAboutme() {
		return aboutme;
	}
	/**
	 * @param aboutme the aboutme to set
	 */
	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the articleDtoList
	 */
	public List<Article> getArticleDtoList() {
		return articleDtoList;
	}
	/**
	 * @param articleDtoList the articleDtoList to set
	 */
	public void setArticleDtoList(List<Article> articleDtoList) {
		this.articleDtoList = articleDtoList;
	}
	/**
	 * @return the studentDto
	 */
	public StudentDto getStudentDto() {
		return studentDto;
	}
	/**
	 * @param studentDto the studentDto to set
	 */
	public void setStudentDto(StudentDto studentDto) {
		this.studentDto = studentDto;
	}
	/**
	 * @return the moderDto
	 */
	public ModeratorDto getModerDto() {
		return moderDto;
	}
	/**
	 * @param moderDto the moderDto to set
	 */
	public void setModerDto(ModeratorDto moderDto) {
		this.moderDto = moderDto;
	}
	
}
