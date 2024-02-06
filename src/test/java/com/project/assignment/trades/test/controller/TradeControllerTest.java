package com.project.assignment.trades.test.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.assignment.trades.TradeAssignment;
import com.project.assignment.trades.bean.TradeBean;
import com.project.assignment.trades.controller.TradeController;
import com.project.assignment.trades.service.TradeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TradeAssignment.class)
public class TradeControllerTest {

	@Mock
	private TradeServiceImpl tradeService;

	@InjectMocks
	private TradeController tradeController;

	protected MockMvc mockMVC;

	@Before
	public void mySetup() {
		MockitoAnnotations.initMocks(this);

		mockMVC = MockMvcBuilders.standaloneSetup(tradeController).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllTrades() throws Exception {
		String uri = "/getAllTrades";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);

		when(tradeService.getAllTrades()).thenReturn(getAllTradesStubData());
		ResultActions resultActions = mockMVC.perform(request);
		MvcResult mvcResult = resultActions.andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		int actualStatus = mvcResult.getResponse().getStatus();

		List<TradeBean> listTrades = convertFromJsonToObject(response, List.class);

		verify(tradeService, times(1)).getAllTrades();

		Assert.assertTrue(listTrades != null);
		Assert.assertTrue(actualStatus == HttpStatus.OK.value());
	}
	
	@Test
	public void addTrade() throws Exception {
		String uri = "/addTrade";
		TradeBean tradeBean = new TradeBean("T5555", 1, "CP-1", "B1", "2020-05-20", "2024-02-05", "N");

		String tradeBeanJsonFormat = convertFromObjectToJson(tradeBean);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(tradeBeanJsonFormat).contentType(MediaType.APPLICATION_JSON);

		ResultActions resultActions = mockMVC.perform(request);
		MvcResult mvcResult = resultActions.andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		int actualStatus = mvcResult.getResponse().getStatus();

		Assert.assertTrue(response != null);
		Assert.assertTrue(actualStatus == HttpStatus.OK.value());
	}
	

	public List<TradeBean> getAllTradesStubData() {
		return Arrays.asList(new TradeBean("T1", 1, "CP-1", "B1", "2020-05-20", "2024-02-05", "N"));
	}

	static public <T> T convertFromJsonToObject(String json, Class<T> var) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, var);
	}
	
	public static String convertFromObjectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

}
