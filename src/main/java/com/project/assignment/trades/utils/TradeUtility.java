package com.project.assignment.trades.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.assignment.trades.exceptions.ErrorResponse;

@Component
public class TradeUtility {

	public static String convertFromObjectToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(e.getMessage());
		}
		return null;
	}
}
