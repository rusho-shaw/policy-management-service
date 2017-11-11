package com.cts.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.application.dao.PolicyRepository;
import com.cts.application.document.Policy;
import com.cts.application.exception.PolicyServiceException;

@Service
public class PolicyService {
	
	@Autowired
	public PolicyRepository policyRepository;

	public boolean addOrUpdate(String policyNumber, String policyName, String policyDetails) {
		boolean isAddedOrUpdated = false;
		
		if(policyNumber != null) {
			Policy policy = new Policy();
			policy.setPolicyNumber(policyNumber);
			policy.setPolicyDetails(policyDetails);
			policy.setPolicyName(policyName);
			policyRepository.save(policy);
			isAddedOrUpdated = true;
		}
		return isAddedOrUpdated;
	}

	public List<Policy> getAllPolicies() {
		List<Policy> policies = policyRepository.findAll();
		return policies;
	}

	public Policy getPolicy(String policyNumber) throws PolicyServiceException {
		Policy policy = policyRepository.findOne(policyNumber);
		if(policy==null) {
			throw new PolicyServiceException("Policy Not Found");
		}
		return policy;
	}
}
