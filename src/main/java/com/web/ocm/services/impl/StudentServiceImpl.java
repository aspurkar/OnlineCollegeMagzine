package com.web.ocm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.ocm.dto.StudentDto;
import com.web.ocm.dto.UserDto;
import com.web.ocm.entities.Student;
import com.web.ocm.entities.User;
import com.web.ocm.repo.StudentRepo;
import com.web.ocm.repo.UserRepo;
import com.web.ocm.services.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService{

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private StudentRepo studentRepo;

	/* (non-Javadoc)
	 * @see com.web.ocm.services.StudentService#getStudentByUserId(int)
	 */
	@Override
	public StudentDto getStudentByUserId(int userId) {
		Student studnt = studentRepo.getByUser(userId);
		StudentDto dto = new StudentDto();
		dto.setRegNumber(studnt.getRegNumber());
		dto.setAlumni(studnt.getAlumni());
		dto.setBranch(studnt.getBranch());
		dto.setBatch(studnt.getBatch());
		dto.setProgram(studnt.getProgram());
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.web.ocm.services.StudentService#updateStudent(int)
	 */
	@Override
	public boolean updateStudent(UserDto userDto) {
		
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
			
			Student studnt = studentRepo.getByUser(user.getUserId());
			
			studnt.setRegNumber(userDto.getStudentDto().getRegNumber());
			studnt.setAlumni(userDto.getStudentDto().getAlumni());
			studnt.setBranch(userDto.getStudentDto().getBranch());
			studnt.setBatch(userDto.getStudentDto().getBatch());
			studnt.setProgram(userDto.getStudentDto().getProgram());
			studnt.setUser(user);
			 studentRepo.save(studnt);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
