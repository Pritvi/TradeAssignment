package com.project.assignment.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.project.assignment.trades.service.TradeServiceImpl;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private TradeServiceImpl TradeServiceImp;

	/**
	 *
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		TradeServiceImp.updateTradeExpiryStatus();
	}
}
