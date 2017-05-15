package com.web.ocm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.ocm.entities.Student;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	
	@Query("select s from Student s where s.user.id =:userId")
	public Student getByUser(@Param("userId") int userId);

}
