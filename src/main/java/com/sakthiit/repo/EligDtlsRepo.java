package com.sakthiit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakthiit.entity.EligDtlsEntity;

public interface EligDtlsRepo extends JpaRepository<EligDtlsEntity, Serializable>{
	
	public List<EligDtlsEntity> findByCaseNum(Long caseNum);

}
