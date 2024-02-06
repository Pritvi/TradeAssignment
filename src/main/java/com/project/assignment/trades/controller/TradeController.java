package com.project.assignment.trades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.assignment.trades.bean.TradeBean;
import com.project.assignment.trades.exceptions.ErrorResponse;
import com.project.assignment.trades.service.TradeServiceImpl;
import com.project.assignment.trades.validator.TradeReqValidator;

@RestController
public class TradeController {

	@Autowired
	private TradeServiceImpl tradeService;

	@Autowired
	private TradeReqValidator tradeReqValidator;

	@RequestMapping(value = "/getAllTrades", method = RequestMethod.GET)
	public List<TradeBean> getAllTrades() {
		return tradeService.getAllTrades();
	}

	@RequestMapping(value = "/addTrade", method = RequestMethod.POST)
	public ResponseEntity<?> addTrade(@RequestBody TradeBean tradeBean) throws Exception {

		if (tradeReqValidator.validateMaturityDate(tradeBean)) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Maturity Date cannot be less than Today's Date");
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		List<TradeBean> tradeList= tradeService.addTrade(tradeBean);
		return new ResponseEntity<>(tradeList, HttpStatus.OK);
	}
	
}
