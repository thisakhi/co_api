package com.sakthiit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sakthiit.service.CoTriggerService;

@RestController
public class CoTriggerRestController {

	@Autowired
	private CoTriggerService coTriggerService;

	@GetMapping("/trg")
	public ResponseEntity<String> processPendingTriggerDtls() {

		String processStatus = coTriggerService.processPendingTriggerDtls();

		return new ResponseEntity<String>(processStatus, HttpStatus.OK);
	}
}
