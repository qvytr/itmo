package com;

import java.lang.Exception;

@SuppressWarnings("serial")
public class MalformedInputException extends Exception {
	@Override
	public String getMessage() {
		return "The input values are malformed";
	}
}
