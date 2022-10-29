package com.sakthiit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ELIGIBILTY_DTLS")
public class EligDtlsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer edTraceId;
	private Long  caseNum;
	private String holderName;
	private Long holderSnn;
	private String planName;
	private String PlanStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double  benefitAmt;
	private String denialReson;
	
	
}
