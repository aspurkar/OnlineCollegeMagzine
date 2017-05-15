package com.web.ocm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.ocm.entities.Moderator;


@Repository
public interface ModeratorRepo extends JpaRepository<Moderator, Long>{
	
	@Query("select m from Moderator m where m.user.id =:userId")
	public Moderator getByUser(@Param("userId") int userId);

}
