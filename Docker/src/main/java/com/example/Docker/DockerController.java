package com.example.Docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {
	
	@Autowired
	DockerRepo dockerRepo;
	
	
	@GetMapping("/status")
	public String getStatus() {
		System.out.println("nitish");
		return "Hello- world cc";
	}
	
	@GetMapping("/newstatus")
	public String getNewStatus() {
		System.out.println("nitish new status");
		return "Hello- world new cc";
	}
	
	@GetMapping("/save")
	public String saveName(String firstname) {
		System.out.println("save");
		DockerPOJO dcokerpojo = new DockerPOJO();
		dcokerpojo.setFirstname(firstname);
		DockerPOJO savepojo = dockerRepo.save(dcokerpojo);
		if(savepojo!=null) {
			return savepojo.getFirstname();
		}else {
			return "Not saved";
		}
		
	}
}
