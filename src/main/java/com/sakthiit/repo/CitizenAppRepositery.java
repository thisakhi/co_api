package com.sakthiit.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sakthiit.entity.CitizenAppEntiry;

 

public interface CitizenAppRepositery extends JpaRepository<CitizenAppEntiry , Serializable> {

}
