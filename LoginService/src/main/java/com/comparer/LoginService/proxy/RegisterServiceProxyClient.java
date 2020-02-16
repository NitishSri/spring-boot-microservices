package com.comparer.LoginService.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.comparer.LoginService.resourceobject.UserDetailsRO;

@FeignClient(name = "register-service")
public interface RegisterServiceProxyClient {

	@GetMapping("/getUserDetails")
	public UserDetailsRO getUserDetails(@RequestParam("username") String username);

}
