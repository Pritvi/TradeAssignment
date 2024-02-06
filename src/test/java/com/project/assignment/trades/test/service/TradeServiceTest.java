package com.project.assignment.trades.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.assignment.trades.TradeAssignment;
import com.project.assignment.trades.bean.TradeBean;
import com.project.assignment.trades.service.TradeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TradeAssignment.class)
public class TradeServiceTest {

	@Autowired
	private TradeServiceImpl tradeService;

	@Test
	public void getAllTrades() throws Exception {

		List<TradeBean> tradeList = tradeService.getAllTrades();
		Assert.assertTrue(tradeList != null);
	}

	@Test
	public void addTradeNewTradeAdded() throws Exception {
		TradeBean tradeToBeAdded = new TradeBean("T4", 1, "CP-10", "B1", "20/05/2020", "05/02/2024", "N");
		List<TradeBean> tradeList = tradeService.addTrade(tradeToBeAdded);
		Assert.assertTrue(tradeList != null);
		Assert.assertTrue(tradeList.size() == 5);
	}
	
	@Test
	public void addTradeTradeNewVersionAdded() throws Exception {
		TradeBean tradeToBeAdded = new TradeBean("T3", 4, "CP-10", "B1", "20/05/2020", "05/02/2024", "N");
		List<TradeBean> tradeList = tradeService.addTrade(tradeToBeAdded);
		Assert.assertTrue(tradeList != null);
		Assert.assertTrue(tradeList.size() == 6);
	}

	@Test
	public void addTradeExistingTradeUpdated() throws Exception {
		TradeBean tradeToBeAdded = new TradeBean("T2", 2, "CP-11111", "B1", "20/05/2020", "05/02/2024", "N");
		List<TradeBean> tradeList = tradeService.addTrade(tradeToBeAdded);
		Assert.assertTrue(tradeList != null);
		Assert.assertTrue(tradeList.size() == 5);
	}

}
