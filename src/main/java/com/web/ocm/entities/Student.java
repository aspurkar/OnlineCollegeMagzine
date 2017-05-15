package com.web.ocm.entities;

import java.io.Serializable;
import javax.persistence.*;



@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STUDENT_ID")
	private int studentId;

	private int alumni;

	private String batch;

	private String branch;

	private String program;

	@Column(name="REG_NUMBER")
	private String regNumber;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public Student() {
	}

	public int getStudentId() {
		return this.studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getAlumni() {
		return this.alumni;
	}

	public void setAlumni(int alumni) {
		this.alumni = alumni;
	}

	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getProgram() {
		return this.program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getRegNumber() {
		return this.regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}