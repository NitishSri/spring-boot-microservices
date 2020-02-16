package com.comparer.zuulapigateway.proxy;

import java.io.Serializable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.comparer.zuulapigateway.resource.UserCredentials;

@FeignClient(name = "login-service")
public interface LoginServiceProxyClient extends Serializable {

	@GetMapping("/getUserDetails")
	public UserCredentials getUserDetails(@RequestParam("username") String username);

}
