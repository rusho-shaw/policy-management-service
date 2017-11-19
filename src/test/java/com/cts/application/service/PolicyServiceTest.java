package com.cts.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.application.dao.PolicyRepository;
import com.cts.application.document.Policy;
import com.cts.application.exception.PolicyServiceException;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class PolicyServiceTest {
	
	@Configuration
	static class PolicyServiceTestContextConfiguration {
		@Bean
		public PolicyService policyService() {
			return new PolicyService();
		}

		@Bean
		public PolicyRepository policyRepository() {
			return Mockito.mock(PolicyRepository.class);
		}
	}
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PolicyRepository policyRepository;
	
	@Test
	public void addOrUpdate() {
		//String policyNumber, String policyName, String policyDetails
		Policy policy = new Policy();
		policy.setPolicyNumber("1");
		policy.setPolicyDetails("dtl");
		policy.setPolicyName("Pol NAme");

		Mockito.when(policyRepository.save(Matchers.any(Policy.class))).thenReturn(policy);
		boolean result = policyService.addOrUpdate("1", "dtl", "Pol NAme");
		assertThat(result).isEqualTo(true);
	}
	@Test
	public void getAllPolicies() {
		//String policyNumber, String policyName, String policyDetails
		Policy policy = new Policy();
		policy.setPolicyNumber("1");
		policy.setPolicyDetails("dtl");
		policy.setPolicyName("Pol NAme");
		List<Policy> policies = new ArrayList<Policy>();
		policies.add(policy);

		Mockito.when(policyRepository.findAll()).thenReturn(policies);
		List<Policy> policiesRes = policyService.getAllPolicies();
		assertThat(policiesRes.size()).isEqualTo(1);
	}
	
	@Test
	public void getPolicy() throws PolicyServiceException {
		Policy policy = new Policy();
		policy.setPolicyNumber("1");
		policy.setPolicyDetails("dtl");
		policy.setPolicyName("Pol NAme");
		Mockito.when(policyRepository.findOne(Matchers.any(String.class))).thenReturn(policy);
		Policy policyRes = policyService.getPolicy("1");
		assertThat(policyRes.getPolicyName()).isEqualTo("Pol NAme");
		
	}
	@Test(expected = PolicyServiceException.class)
	public void getPolicyNull() throws PolicyServiceException {
		Policy policy = new Policy();
		policy.setPolicyNumber("1");
		policy.setPolicyDetails("dtl");
		policy.setPolicyName("Pol NAme");
		Mockito.when(policyRepository.findOne(Matchers.any(String.class))).thenReturn(null);
		policyService.getPolicy("1");
		//assertThat(policyRes.getPolicyName()).isEqualTo("Pol NAme");
		
	}

}
