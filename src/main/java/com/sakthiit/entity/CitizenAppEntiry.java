package com.sakthiit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITIZEN_APPS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenAppEntiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appid;
	private String fullname;
	private String email;
	private String phno;
	private Long ssn;
	private LocalDate dob;
	private String gender;
	private String statename;

	private String createdBy;
	private String updatedBy;

	@UpdateTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;

}
