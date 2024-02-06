package com.project.assignment.trades.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.project.assignment.trades.bean.TradeBean;

@Component
public class TradeReqValidator {

	public boolean validateMaturityDate(TradeBean tradeBean) {

		if (LocalDate.parse(tradeBean.getMaturityDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				.isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}

}
