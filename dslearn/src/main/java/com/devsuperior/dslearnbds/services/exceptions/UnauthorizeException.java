package com.devsuperior.dslearnbds.services.exceptions;

public class UnauthorizeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	// trata errro 401
	public UnauthorizeException(String msg) {
		super(msg);
	}

}
