package com.cts.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.application.document.Policy;
import com.cts.application.service.PolicyService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PolicyServiceController.class, secure = false)
public class PolicyControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PolicyService policyService;
	
	@Test
	public void getAllPolicies() throws Exception {

		List<Policy> policies = new ArrayList<Policy>();
		Policy policy1 = new Policy();
		policy1.setPolicyName("ABCD");
		policy1.setPolicyNumber("1");
		policy1.setPolicyDetails("DTL");
		policies.add(policy1);
		Mockito.when(policyService.getAllPolicies()).thenReturn(policies);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policies/getAllPolicies/");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());

		String expected = "{\"policies\":[{\"policyNumber\":\"1\",\"policyName\":\"ABCD\",\"policyDetails\""
				+ ":\"DTL\"}],\"message\":\"Policies found successfully\",\"status\":\"1\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	@Test
	public void getAllPoliciesNull() throws Exception {

		Mockito.when(policyService.getAllPolicies()).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policies/getAllPolicies/");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("No policies:..." +result.getResponse().getContentAsString());

		String expected = "{\"policies\":null,\"message\":\"Policy not updated\",\"status\":\"0\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	@Test
	public void getPolicy() throws Exception {

		Policy policy1 = new Policy();
		policy1.setPolicyName("ABCD");
		policy1.setPolicyNumber("1");
		policy1.setPolicyDetails("DTL");
		Mockito.when(policyService.getPolicy(Matchers.any(String.class))).thenReturn(policy1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policies/getPolicy/")
				.param("policyId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("getPolicy......"+result.getResponse().getContentAsString());

		String expected = "{\"message\":\"Policy found successfully\",\"status\":\"1\",\"policy\""
				+ ":{\"policyNumber\":\"1\",\"policyName\":\"ABCD\",\"policyDetails\":\"DTL\"}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	@Test
	public void getPolicyNull() throws Exception {

		Mockito.when(policyService.getPolicy(Matchers.any(String.class))).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policies/getPolicy/")
				.param("policyId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("getPolicyNull......"+result.getResponse().getContentAsString());

		String expected = "{\"message\":\"Policy not found\",\"status\":\"0\",\"policy\":null}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}
	@Test
	public void addOrUpdate() throws Exception {
		Mockito.when(policyService.addOrUpdate(Matchers.any(String.class), Matchers.any(String.class)
				, Matchers.any(String.class))).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/policies/addOrUpdate/")
				.param("policyNumber", "1")
				.param("policyName", "ABC")
				.param("policyDetails", "Pol Dtl");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("addOrUpdate......"+result.getResponse().getContentAsString());

		String expected = "{\"message\":\"Policy updated successfully\",\"status\":\"1\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	@Test
	public void addOrUpdateNot() throws Exception {
		Mockito.when(policyService.addOrUpdate(Matchers.any(String.class), Matchers.any(String.class)
				, Matchers.any(String.class))).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/policies/addOrUpdate/")
				.param("policyNumber", "1")
				.param("policyName", "ABC")
				.param("policyDetails", "Pol Dtl");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("addOrUpdateNot......"+result.getResponse().getContentAsString());

		String expected = "{\"message\":\"Policy not updated\",\"status\":\"0\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
