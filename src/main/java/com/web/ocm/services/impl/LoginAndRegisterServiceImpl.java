package com.web.ocm.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.ocm.dto.LoginDto;
import com.web.ocm.dto.RegisterDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Student;
import com.web.ocm.entities.User;
import com.web.ocm.repo.StudentRepo;
import com.web.ocm.repo.UserRepo;
import com.web.ocm.services.LoginAndRegisterService;


@Service("loginService")
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

	private static final Logger logger = LoggerFactory.getLogger(LoginAndRegisterServiceImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired 
	private StudentRepo studentRepo;

	@PersistenceContext
	EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.web.ocm.services.LoginAndRegisterService#verifyLogin()
	 */
	@Override
	@Transactional
	public UserDto verifyLogin(LoginDto loginDto) {
		User user = null;
		try {
			user = userRepo.findByUserNameAndPassword(loginDto.getuName().trim(), loginDto.getPwd());
			if(user != null){
				UserDto userDto = new UserDto();
				userDto.setUserId(user.getUserId());
				userDto.setFname(user.getNameFirst());
				userDto.setLname(user.getNameLast());
				userDto.setAddress1(user.getAddress());
				userDto.setCity(user.getCity());
				userDto.setState(user.getState());
				userDto.setCountry(user.getCountry());
				userDto.setPincode(String.valueOf(user.getPinCode()));
				userDto.setEmail(user.getEmail());
				userDto.setUname(user.getUserName());
				userDto.setSquestion(user.getSecurityQuestion());
				userDto.setSanswer(user.getSecurityAnswer());
				userDto.setUserType(user.getType());

				return userDto;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean userAlreadyExists(String userName){

		try {
			User user = userRepo.findByUserName(userName);
			if(user == null){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.web.ocm.services.LoginAndRegisterService#registerNewUser()
	 */
	@Override
	@Transactional
	public User registerNewUser(RegisterDto registerDto) {
		logger.info("<<<< registerNewUser() >>>>");
		String address="";
		User user = null;
		try {
			if(!userAlreadyExists(registerDto.getUname())){
				if(registerDto.getAddress1() !=null && !registerDto.getAddress1().isEmpty()){
					address = registerDto.getAddress1();
				}
				if(registerDto.getAddress2() !=null && !registerDto.getAddress2().isEmpty()){
					address = address+" "+registerDto.getAddress2();
				}
				if(registerDto.getAddress3() !=null && !registerDto.getAddress3().isEmpty()){
					address = address+" "+registerDto.getAddress3();
				}
				user = new User();
				user.setAboutMe(registerDto.getAboutme());
				user.setAddress(address);
				user.setCity(registerDto.getCity());
				user.setState(registerDto.getState());
				user.setCountry(registerDto.getCountry());
				user.setPinCode(Integer.parseInt(registerDto.getPincode()));
				user.setEmail(registerDto.getEmail());
				user.setUserName(registerDto.getUname());
				user.setType(registerDto.getUserType());
				user.setPassword(registerDto.getPwd());
				user.setSecurityQuestion(registerDto.getSquestion());
				user.setSecurityAnswer(registerDto.getSanswer());
				user.setNameFirst(registerDto.getFname());
				user.setNameLast(registerDto.getLname());

				user = userRepo.saveAndFlush(user);

				return user;
			}else{
				return new User();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.LoginAndRegisterService#saveStudent()
	 */
	@Override
	@Transactional
	public Student saveStudent(User user,RegisterDto registerDto) {
		Student student = new Student();
		student.setRegNumber(registerDto.getStudentDto().getRegNumber());
		student.setAlumni(registerDto.getStudentDto().getAlumni());
		student.setBranch(registerDto.getStudentDto().getBranch());
		student.setBatch(registerDto.getStudentDto().getBatch());
		student.setProgram(registerDto.getStudentDto().getProgram());
		student.setUser(user);
		studentRepo.save(student);
		return student;
	}
}
