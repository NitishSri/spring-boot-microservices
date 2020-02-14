package com.example.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.resourceobject.UserDetailsRO;

@FeignClient(name = "register-service")
public interface RegisterServiceProxyClient {

	@GetMapping("/getUserDetails")
	public UserDetailsRO getUserDetails(@RequestParam("username") String username);

}
