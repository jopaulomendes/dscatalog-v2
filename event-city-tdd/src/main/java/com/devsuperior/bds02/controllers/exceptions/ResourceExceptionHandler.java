package com.devsuperior.bds02.controllers.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.exceptions.DataBaseException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler (EntityNotFoundException.class)
	public ResponseEntity<CityDTO> entityNotFound(){
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler (DataBaseException.class)
	public ResponseEntity<CityDTO> database(DataBaseException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
