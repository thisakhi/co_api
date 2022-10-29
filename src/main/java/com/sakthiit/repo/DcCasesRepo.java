package com.sakthiit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakthiit.entity.DcCasesEntity;

public interface DcCasesRepo extends JpaRepository<DcCasesEntity, Serializable> {
	
	public DcCasesEntity findByAppId(Integer appId);

}


