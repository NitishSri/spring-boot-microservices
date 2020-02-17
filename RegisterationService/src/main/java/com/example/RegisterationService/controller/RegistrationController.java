package com.example.RegisterationService.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.RegisterationService.proxy.LoginServiceProxyClient;
import com.example.RegisterationService.repository.RegistrationRepository;
import com.example.RegisterationService.resourceobject.RegisterationUserRequestRO;
import com.example.RegisterationService.resourceobject.RegisterationUserResponseRO;
import com.example.RegisterationService.resourceobject.SuccessReport;
import com.example.RegisterationService.resourceobject.UserDetailsRO;
import com.example.RegisterationService.service.UserRegistrationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class RegistrationController {

	private RegistrationRepository registrationRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserRegistrationService service;

	@Autowired
	LoginServiceProxyClient loginProxy;

	@Value("${login.service.name}")
	private String loginServiceName;

	@Value("${api.gateway.url}")
	private String apiGatewayURL;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	Environment environment;

	public RegistrationController(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

	@PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterationUserRequestRO registerUserRO) {
		logger.info("register user api called");

		/**
		 * Using RestTemplate making a call to another microservice final String
		 * checkUserUri = "http://" + loginServiceName + "/checkUserExist";
		 * UriComponentsBuilder builder =
		 * UriComponentsBuilder.fromUriString(checkUserUri).queryParam("username",username);
		 * String apiUrl = builder.toUriString(); SuccessReport responseEntity =
		 * restTemplate.getForObject(apiUrl, SuccessReport.class);
		 **/

		if (service.checkUsernameTaken(registerUserRO.getUsername())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist in our system.");
		}

		/**
		 * Using RestTemplate making a call to another microservice final String
		 * addUserUri = "http://" + loginServiceName + "/addUser"; UriComponentsBuilder
		 * addUserbuilder =
		 * UriComponentsBuilder.fromUriString(addUserUri).queryParam("username",
		 * username).queryParam("password", password); String addUserUrl =
		 * addUserbuilder.toUriString();
		 **/

		SuccessReport addUserEntity = loginProxy.addUser(registerUserRO.getUsername(), registerUserRO.getPassword());
		if (null != addUserEntity && "USERNOTADDED".equals(addUserEntity.getStatusCode())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(addUserEntity.getStatusMessage());
		}

		RegisterationUserRequestRO user = new RegisterationUserRequestRO();
		user.setFirstname(registerUserRO.getFirstname());
		user.setLastname(registerUserRO.getLastname());
		user.setUsername(registerUserRO.getUsername());

		RegisterationUserResponseRO registeredUser = service.saveUser(user);
		String message = org.apache.commons.lang.StringUtils.EMPTY;
		if (null != registeredUser) {
			message = registeredUser.getFirstname() + " " + registeredUser.getLastname()
					+ " has been registered successfully !!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message = "Oops something went wrong with registration service";
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
		}

	}

	@GetMapping("/status")
	@HystrixCommand(fallbackMethod = "fallBackLoginService")
	public String checkLoginServiceStatus() {
		logger.info("status api called");
		logger.info("server port => {} ", environment.getProperty("local.server.port"));

		final String statusUri = "http://" + loginServiceName + "/status";
		// final String statusUri = apiGatewayURL +"/"+ loginServiceName + "/status";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(statusUri);
		String apiUrl = builder.toUriString();
		String responseEntity = restTemplate.getForObject(apiUrl, String.class);
		if (responseEntity != null) {
			return "Login Service is running !!";
		}
		return "Nothing";
	}

	public String fallBackLoginService() {
		logger.info("fallback method called");
		return "Login Service is Under Maintenance";
	}

	@GetMapping("/getUserDetails")
	public UserDetailsRO getUserDetails(String username) {
		return service.getUserDetails(username);

	}

	@PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public UserDetailsRO updateUserDetails(@RequestBody UserDetailsRO user) {
		return service.updateUserDetails(user);

	}

	@GetMapping("/clearcache")
	public String clearCache() {
		for (String name : cacheManager.getCacheNames()) {
			cacheManager.getCache(name).clear(); // clear cache by name
		}
		return "All cache has been deleted";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(String username) {
		return service.deleteUser(username);
	}

}
