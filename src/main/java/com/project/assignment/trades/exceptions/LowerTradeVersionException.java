package com.project.assignment.trades.exceptions;

import org.springframework.stereotype.Component;

@Component
public class LowerTradeVersionException extends RuntimeException {

	/**
	 * Added serialVersionUID
	 */
	private static final long serialVersionUID = 7089842705409919611L;

	public LowerTradeVersionException() {
		super();
	}
}
