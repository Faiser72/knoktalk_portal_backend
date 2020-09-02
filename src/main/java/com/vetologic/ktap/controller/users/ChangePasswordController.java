package com.vetologic.ktap.controller.users;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vetologic.ktap.beans.response.KtapResponse;
import com.vetologic.ktap.beans.users.AdminBean;
import com.vetologic.ktap.model.service.users.AdminService;
import com.vetologic.ktap.utils.MailUtil;





@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class ChangePasswordController {

	private static Logger log = LoggerFactory.getLogger(ChangePasswordController.class);

	@Autowired
	private AdminService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	MailUtil mailUtil;

	@PutMapping(path = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public KtapResponse changePassword(KtapResponse cmsResponse, Principal principal,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {
		try {
			AdminBean user = userService.getUserByName(principal.getName());
			if (user != null) {
				boolean isPasswordMatches = passwordEncoder.matches(currentPassword, user.getPassword());
				if (isPasswordMatches) {
					String hashedPassword = passwordEncoder.encode(newPassword);
					user.setPassword(hashedPassword);
					if (userService.update(user)) {
						try {
							mailUtil.sendChangePasswordEmail(user, newPassword);
							log.info("Password Changed Successfully & Sent Email Successfully");
						} catch (Exception e) {
							log.info("Password Changed Successfully & fails to send Email");
							log.error(e.getMessage());
						}
						cmsResponse.setSuccess(true);
						cmsResponse.setMessage("Password Changed Successfully");
					} else {
						cmsResponse.setSuccess(false);
						log.info("Fails to Change Password");
						cmsResponse.setMessage("Fails to Change Password");
					}
				} else {
					cmsResponse.setSuccess(false);
					log.info("Incorrect Current Password");
					cmsResponse.setMessage("Incorrect Current Password");
				}
			} else {
				cmsResponse.setSuccess(false);
				log.info("Something Went Wrong");
				cmsResponse.setMessage("Something Went Wrong! Try again.");
			}
		} catch (Exception e) {
			cmsResponse.setSuccess(false);
			log.info("Something Went Wrong");
			cmsResponse.setMessage("Something Went Wrong! Try again.");
			log.error(e.getMessage());
		}
		return cmsResponse;
	}
}
