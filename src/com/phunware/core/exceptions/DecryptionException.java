package com.phunware.core.exceptions;

public class DecryptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public DecryptionException(String errorMsg) {
		super(errorMsg);
	}
}
