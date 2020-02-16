package com.example.RegisterationService.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RegisterationService.resourceobject.SuccessReport;

@FeignClient(name = "login-service")
public interface LoginServiceProxyClient {

	@GetMapping("/addUser")
	public SuccessReport addUser(@RequestParam("username") String username, @RequestParam("password") String password);

}
