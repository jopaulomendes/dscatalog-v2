package com.devsuperior.dslearnbds.services.exceptions;

public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	// trata erro 403
	public ForbiddenException(String msg) {
		super(msg);
	}

}
