package com.project.assignment.trades.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.project.assignment.trades.bean.TradeBean;
import com.project.assignment.trades.exceptions.LowerTradeVersionException;
import com.project.assignment.trades.utils.TradeUtility;

/**
 * @author prithvi.tharanath
 *
 */
@Service
public class TradeServiceImpl {

	private List<TradeBean> tradeList = new ArrayList<TradeBean>(
			Arrays.asList(new TradeBean("T1", 1, "CP-1", "B1", "20/05/2020", "05/02/2024", "N"),
					new TradeBean("T2", 2, "CP-2", "B1", "20/05/2021", "05/02/2024", "N"),
					new TradeBean("T2", 1, "CP-1", "B1", "20/05/2021", "14/03/2015", "N"),
					new TradeBean("T3", 3, "CP-3", "B2", "20/05/2014", "05/02/2024", "Y")));

	/**
	 * This method fetches all the trade details.
	 * 
	 * @return the list of trades
	 */
	public List<TradeBean> getAllTrades() {
		return tradeList;
	}

	/**
	 * Adds a new trade
	 * 
	 * @param tradeToBeAdded
	 * @throws Exception
	 */
	public List<TradeBean> addTrade(TradeBean tradeToBeAdded) throws Exception {

		// Checks if the tradeId of the tradeToBeAdded already exists in the store.
		boolean isTradeAlreadyExists = tradeList.stream()
				.anyMatch(element -> element.getTradeId().equalsIgnoreCase(tradeToBeAdded.getTradeId()));

		// if the tradeId doesn't exists it is added to the store
		if (!isTradeAlreadyExists) {
			tradeList.add(tradeToBeAdded);
		} else {

			// if the tradeId exists, then we get all the Trades with the same TradeId.
			// Among them the TradeId which has the maximum version is filtered.
			TradeBean tradeWithMaxVersion = tradeList.stream()
					.filter(element -> element.getTradeId().equalsIgnoreCase(tradeToBeAdded.getTradeId()))
					.max(Comparator.comparing(TradeBean::getVersion)).orElseThrow(NoSuchElementException::new);

			// We check if the version of the tradeToBeAdded is lower than the version of
			// the trade in the store. if its lower, then we throw an exception
			// else the tradeToBeAdded is updated into the store
			if (tradeToBeAdded.getVersion() < tradeWithMaxVersion.getVersion()) {
				throw new LowerTradeVersionException();
			} else if (tradeToBeAdded.getVersion() > tradeWithMaxVersion.getVersion()) {
				tradeList.add(tradeToBeAdded);
			} else {
				tradeList.set(tradeList.indexOf(tradeWithMaxVersion), tradeToBeAdded);
			}
		}
		return tradeList;

	}

	/**
	 * updates the expire flag to " Y' if the created date of the trade in the store
	 * crosses the maturity date
	 * 
	 */
	public void updateTradeExpiryStatus() {

		// Added the sysout statements to show initial data and data after updating the
		// expire flag
		// TODO : Remove this and add logger statements
		System.out.println(TradeUtility.convertFromObjectToJson(tradeList));
		System.out.println("-----------------------------------------");

		for (TradeBean trade : tradeList) {
			if (LocalDate.parse(trade.getCreatedDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
					.isAfter(LocalDate.parse(trade.getMaturityDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
				if ("N".equals(trade.getExpired())) {
					trade.setExpired("Y");
				}
			}
		}
		System.out.println(TradeUtility.convertFromObjectToJson(tradeList));
	}

}
