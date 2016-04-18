package com.aqiang.bsms.exception;

public class AttachmentNotFindException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AttachmentNotFindException() {
	}

	public AttachmentNotFindException(String message) {
		super(message);
	}

}
