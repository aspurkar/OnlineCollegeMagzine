/**
 * 
 */
package com.web.ocm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ocm.entities.User;


public interface UserRepo extends JpaRepository<User, Long> {

	public User findByUserId(int uid);
	public User findByUserNameAndPassword(String uName, String pwd);
	public User findByUserName(String uName);
}
