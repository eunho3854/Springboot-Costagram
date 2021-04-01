package com.cos.costagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{

	@Query(value = "select * from image where userId in (select toUserId from follow where fromUserId = :principalId) order by id desc", nativeQuery = true)
	List<Image> mFeed(int principalId);
	
	@Query(value = "SELECT * FROM image i INNER JOIN likes l ON i.id = l.imageId WHERE i.userId != :principalId GROUP BY i.id ORDER BY count(l.imageId) DESC;", nativeQuery = true)
	List<Image> mExplore(int principalId);
}
