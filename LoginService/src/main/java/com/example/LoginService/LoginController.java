package com.example.LoginService;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.LoginCredentials;
import com.example.properties.RabbitMQProperties;
import com.example.resourceobject.ForgotPasswordRO;
import com.example.resourceobject.SuccessReport;
import com.example.service.DataService;

@RestController
public class LoginController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQProperties rabbitMQProperties;

	@Autowired
	private DataService dataService;

	@GetMapping("/status")
	public String checkStatus() {
		logger.info("status api called ");
		return "Service is running !!";
	}

	@GetMapping("/login")
	public String login(String username, String password) {
		logger.info("login api called");
		String response = Strings.EMPTY;
		if (!validateInput(username, password)) {
			response = "Username or password cannot be null !!";
		}

		if (dataService.checkUserExist(username, password)) {
			response = "Success User is found";
		} else {
			response = "No user found !!";
		}
		return response;
	}

	@GetMapping("/addUser")
	public SuccessReport addUserCred(String username, String password) {
		logger.info("addUser api called");
		LoginCredentials loginUser = dataService.addUser(username, password);
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
	public void sendMessage(String email){
        ForgotPasswordRO passRO = new ForgotPasswordRO();
        passRO.setEmail(email);
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), passRO);
    }

}
