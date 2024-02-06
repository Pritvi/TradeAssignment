package com.project.assignment.trades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.assignment.trades.bean.TradeBean;

public class TestJava {

	public static void main(String[] args) throws Exception {

		List<TradeBean> tradeList = new ArrayList<TradeBean>(
				Arrays.asList(new TradeBean("T1", 1, "CP-1", "B1", "20/05/2020", "05/02/2024", "N"),
						new TradeBean("T2", 2, "CP-2", "B1", "20/05/2021", "05/02/2024", "N"),
						new TradeBean("T2", 1, "CP-1", "B1", "20/05/2021", "14/03/2015", "N"),
						new TradeBean("T3", 3, "CP-3", "B2", "20/05/2014", "05/02/2024", "Y")));

		TradeBean trade = new TradeBean("T3", 4 , "CP-10", "B1", "20/05/2020", "05/02/2024", "N");

		
		boolean isExist = tradeList.stream()
				.anyMatch(element -> element.getTradeId().equalsIgnoreCase(trade.getTradeId()));
		System.out.println(isExist);
		if (!isExist) { 
			System.out.println(trade);
			
		}else {
			
			TradeBean tradeWithMaxVersion = tradeList.stream()
					.filter(element -> element.getTradeId().equalsIgnoreCase(trade.getTradeId()))
					.max(Comparator.comparing(TradeBean::getVersion)).orElseThrow(NoSuchElementException::new);
			 System.out.println("Max age is" +tradeWithMaxVersion);
			 
						
			 if(trade.getVersion()<tradeWithMaxVersion.getVersion()) {
				 throw new Exception();
			 }
			 else {
			 
			  int x = tradeList.indexOf(tradeWithMaxVersion); 
			  tradeList.set(x, trade);
			  
			  System.out.println(convertFromObjectToJson("index ="+x));
			  System.out.println(convertFromObjectToJson(tradeList));
			 }
		}
		 		
		/*
		 * List<TradeBean> trades = tradeList.stream() .filter(element ->
		 * element.getTradeId().equalsIgnoreCase(trade.getTradeId())) .filter(e ->
		 * trade.getVersion() >= e.getVersion()) .collect(Collectors.toList());
		 */
		 
		/*
		 * List<TradeBean> trades = tradeList.stream() .filter(element ->
		 * element.getTradeId().equalsIgnoreCase(trade.getTradeId()))
		 * .collect(Collectors.toList());
		 * System.out.println(convertFromObjectToJson(trades));
		 */

   
        

		/*
		 * int version = trade.getVersion();
		 * 
		 * if(trades.isEmpty() && trades.size()==1) { tradeList.add(trade); } else {
		 * 
		 * TradeBean maxAge = trades .stream()
		 * .max(Comparator.comparing(TradeBean::getVersion))
		 * .orElseThrow(NoSuchElementException::new);
		 * 
		 * System.out.println("Max age is" +maxAge);
		 * 
		 * }
		 */
		
		
		/*
		 * List<TradeBean> trades = tradeList.stream() .filter(c -> { try {
		 * if(trade.getVersion() > c.getVersion())
		 * 
		 * 
		 * 
		 * return (Boolean) null; } catch (Exception e) { //handle exception } return
		 * false; }) .collect(Collectors.toList());
		 */
		
		//System.out.println(convertFromObjectToJson(trades));

		// extracted(tradeList);
	}
	
	

	private static void extracted(List<TradeBean> tradeList) throws JsonProcessingException {
		System.out.println(tradeList.toString());

		System.out.println("-----------------------------------------");

		for (TradeBean trade : tradeList) {

			if (LocalDate.parse(trade.getCreatedDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
					.isAfter(LocalDate.parse(trade.getMaturityDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
				if ("N".equals(trade.getExpired())) {
					trade.setExpired("Y");
				}
			}

		}

		System.out.println(convertFromObjectToJson(tradeList));
	}

	public static String convertFromObjectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

}
