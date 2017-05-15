package com.web.ocm.dto;

import java.io.Serializable;

import com.web.ocm.entities.Student;


public class StudentDto  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer alumni;

	private String batch;

	private String branch;

	private String program;

	private String regNumber;

	

	public Integer getAlumni() {
		return alumni;
	}

	public void setAlumni(Integer alumni) {
		this.alumni = alumni;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	
	
}
