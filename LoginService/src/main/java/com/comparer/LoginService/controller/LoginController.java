package com.comparer.LoginService.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.comparer.LoginService.model.LoginCredentials;
import com.comparer.LoginService.properties.RabbitMQProperties;
import com.comparer.LoginService.proxy.RegisterServiceProxyClient;
import com.comparer.LoginService.resourceobject.ForgotPasswordRO;
import com.comparer.LoginService.resourceobject.LoginRequestRO;
import com.comparer.LoginService.resourceobject.LoginResponseRO;
import com.comparer.LoginService.resourceobject.SuccessReport;
import com.comparer.LoginService.resourceobject.UserDetailsRO;
import com.comparer.LoginService.security.SecurityUtilities;
import com.comparer.LoginService.service.LoginService;

@RestController
public class LoginController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${jwt.signing.key}")
	private String jwtTokenKey;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	RabbitMQProperties rabbitMQProperties;

	@Autowired
	RegisterServiceProxyClient registerClient;

	@Autowired
	private LoginService loginService;

	@GetMapping("/status")
	public String checkStatus() {
		logger.info("status api called ");
		return "Service is running !!";
	}

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserDetailsRO> login(@Valid @RequestBody LoginRequestRO loginRO) {
		logger.info("login api called");
		UserDetails user = loginService.loadUserByUsername(loginRO.getUsername());
		HttpHeaders headers = new HttpHeaders();
		UserDetailsRO userRO = new UserDetailsRO();
		if (null != user && encoder.matches(loginRO.getPassword(), user.getPassword())) {

			final String token = SecurityUtilities.getInstance()
					.generateToken(new org.springframework.security.core.userdetails.User(loginRO.getUsername(),
							loginRO.getPassword(), new ArrayList<>()), jwtTokenKey);
			System.err.println(token);
			headers.add("token", token);
			userRO = registerClient.getUserDetails(loginRO.getUsername());
		} else {
			userRO.setMessage("User not found !!");
		}

		// return ResponseEntity.status(HttpStatus.CREATED).body(userRO);
		return new ResponseEntity<>(userRO, headers, HttpStatus.CREATED);

	}

	@GetMapping("/addUser")
	public SuccessReport addUserCred(String username, String password) {
		logger.info("addUser api called");
		LoginCredentials loginUser = loginService.addUser(username, encoder.encode(password));
		SuccessReport report = new SuccessReport();
		if (loginUser != null) {
			report.setStatusCode("USERADDED");
			report.setStatusMessage("User Cred has been added");
			return report;
		} else {
			report.setStatusCode("USERNOTADDED");
			report.setStatusMessage("User Cred has not been added");
			return report;
		}

	}

	@GetMapping("/getUserDetails")
	public LoginResponseRO getUserDetails(String username) {
		UserDetails user = loginService.loadUserByUsername(username);
		LoginResponseRO userRO = new LoginResponseRO();
		userRO.setUsername(user.getUsername());
		userRO.setPassword(user.getPassword());
		return userRO;
	}

	public boolean validateInput(String username, String password) {
		if (Strings.isBlank(username) || Strings.isBlank(password)) {
			return false;
		}
		return true;
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
		return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/forgotpassword")
	public void sendMessage(String email) {
		ForgotPasswordRO passRO = new ForgotPasswordRO();
		passRO.setEmail(email);
		rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), passRO);
	}

}
