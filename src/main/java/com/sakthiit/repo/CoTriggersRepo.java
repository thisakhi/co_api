package com.sakthiit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakthiit.entity.CoTriggersEntity;

public interface CoTriggersRepo extends JpaRepository<CoTriggersEntity, Serializable> {

	public List<CoTriggersEntity> findByTrgStatus(String trgStatus);
}
