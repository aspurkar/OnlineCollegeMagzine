/**
 * 
 */
package com.web.ocm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ocm.entities.RefCategory;


public interface RefCategoryRepo extends JpaRepository<RefCategory, Long> {

	RefCategory findByCategoryId(int catId);
}
