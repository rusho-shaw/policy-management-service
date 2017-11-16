package com.cts.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.application.document.Policy;
import com.cts.application.exception.PolicyServiceException;
import com.cts.application.service.PolicyService;

@RestController
@RequestMapping("/policies")
public class PolicyServiceController {

	@Autowired
	private PolicyService policyService;
	
	@CrossOrigin
	@RequestMapping("/addOrUpdate")
	public Map<String, Object> addOrUpdate(
			@RequestParam String policyNumber,
			@RequestParam String policyName,
			@RequestParam String policyDetails) {
		
		boolean isPolicyUpdated = policyService.addOrUpdate(policyNumber,
				policyName, policyDetails);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(isPolicyUpdated) {
			dataMap.put("message", "Policy updated successfully");
			dataMap.put("status", "1");
		}else {
			dataMap.put("message", "Policy not updated");
			dataMap.put("status", "0");
		}
		return dataMap;
	}

	@CrossOrigin
	@RequestMapping("/getAllPolicies")
	public Map<String, Object> getAllPolicies() {
		List<Policy> policies = policyService.getAllPolicies();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(policies != null && policies.size() >0) {
			dataMap.put("message", "Policies found successfully");
			dataMap.put("status", "1");
		}else {
			dataMap.put("message", "Policy not updated");
			dataMap.put("status", "0");
		}
		dataMap.put("policies", policies);
		return dataMap;
	}
	@CrossOrigin
	@RequestMapping("/getPolicy")
	public Map<String, Object> getPolicy(@RequestParam String policyId) throws PolicyServiceException {
		Policy policy = policyService.getPolicy(policyId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(policy != null) {
			dataMap.put("message", "Policy found successfully");
			dataMap.put("status", "1");
		}else {
			dataMap.put("message", "Policy not found");
			dataMap.put("status", "0");
		}
		dataMap.put("policy", policy);
		return dataMap;
	}
	/*@RequestMapping("/read")
	public Map<String, Object> read(@RequestParam String policyId) {
		Policy policy = policyRepository.findOne(policyId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Policy found successfully");
		dataMap.put("status", "1");
		dataMap.put("policy > ", policy);
		return dataMap;
	}

	@RequestMapping("/update")
	public Map<String, Object> update(@RequestParam String policyId, @RequestParam String policyDetails) {
		Policy policy = policyRepository.findOne(policyId);
		policy.setPolicyDetails(policyDetails);
		policy = policyRepository.save(policy);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Policy updated successfully");
		dataMap.put("status", "1");
		dataMap.put("Policy", policy);
		return dataMap;
	}

	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String policyId) {
		policyRepository.delete(policyId);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("message", "Policy deleted successfully");
		dataMap.put("status", "1");
		return dataMap;
	}

	*/
}
