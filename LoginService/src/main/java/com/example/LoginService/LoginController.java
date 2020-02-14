package com.example.LoginService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.model.LoginCredentials;
import com.example.properties.RabbitMQProperties;
import com.example.proxy.RegisterServiceProxyClient;
import com.example.resourceobject.ForgotPasswordRO;
import com.example.resourceobject.LoginRequestRO;
import com.example.resourceobject.LoginResponseRO;
import com.example.resourceobject.SuccessReport;
import com.example.resourceobject.UserDetailsRO;
import com.example.service.DataService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	RabbitMQProperties rabbitMQProperties;

	@Autowired
	RegisterServiceProxyClient registerClient;

	@Autowired
	private DataService dataService;

	@GetMapping("/status")
	public String checkStatus() {
		logger.info("status api called ");
		return "Service is running !!";
	}

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserDetailsRO> login(@Valid @RequestBody LoginRequestRO loginRO) {
		logger.info("login api called");
		LoginResponseRO user = dataService.checkUserExist(loginRO.getUsername());
		HttpHeaders headers = new HttpHeaders();
		UserDetailsRO userRO = new UserDetailsRO();
		if (null != user && encoder.matches(loginRO.getPassword(), user.getPassword())) {

			final String token = generateToken(new org.springframework.security.core.userdetails.User(
					loginRO.getUsername(), loginRO.getPassword(), new ArrayList<>()));
			System.err.println(token);
			headers.add("token", token);
			userRO = registerClient.getUserDetails(loginRO.getUsername());
		} else {
			userRO.setMessage("User not found !!");
		}

		// return ResponseEntity.status(HttpStatus.CREATED).body(userRO);
		return new ResponseEntity<>(userRO, headers, HttpStatus.CREATED);

	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, "123".getBytes()).compact();
	}

	@GetMapping("/addUser")
	public SuccessReport addUserCred(String username, String password) {
		logger.info("addUser api called");
		LoginCredentials loginUser = dataService.addUser(username, encoder.encode(password));
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

	@GetMapping("/checkUserExist")
	public SuccessReport checkUsernameExist(String username) {
		logger.info("checkUserExist api called");
		SuccessReport report = new SuccessReport();
		if (dataService.checkUsernameTaken(username)) {
			report.setStatusCode("USEREXIST");
			report.setStatusMessage("Username already exist !!");
		}
		return report;
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
