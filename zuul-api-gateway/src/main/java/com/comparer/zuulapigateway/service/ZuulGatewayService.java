package com.comparer.zuulapigateway.service;

import com.comparer.zuulapigateway.resource.UserCredentials;

public interface ZuulGatewayService {

	public UserCredentials getUserDetails(String username);
}
