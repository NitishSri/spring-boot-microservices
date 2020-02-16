package com.comparer.zuulapigateway.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comparer.zuulapigateway.proxy.LoginServiceProxyClient;
import com.comparer.zuulapigateway.resource.UserCredentials;
import com.comparer.zuulapigateway.service.ZuulGatewayService;

@Service
public class ZuulGatewayServiceImpl implements ZuulGatewayService {

	@Autowired
	LoginServiceProxyClient proxy;

	@Override
	public UserCredentials getUserDetails(String username) {
		return proxy.getUserDetails(username);
	}

}
