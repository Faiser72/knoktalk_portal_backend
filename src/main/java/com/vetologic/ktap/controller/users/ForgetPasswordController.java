package com.vetologic.ktap.controller.users;

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
import com.vetologic.ktap.utils.PasswordUtil;





@RestController
@CrossOrigin(origins = "*")
public class ForgetPasswordController {
	private static Logger log = LoggerFactory.getLogger(ForgetPasswordController.class);

	@Autowired
	private AdminService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	MailUtil mailUtil;

	@PutMapping(path = "/forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public KtapResponse forgotPassword(KtapResponse cmsResponse, @RequestParam("forgotPasswordEmailId") String forgotPasswordEmailId) {
		try {
			AdminBean user = userService.getUserDetailsByEmailId(forgotPasswordEmailId.toLowerCase());
			if (user != null) {
				String generatedPassword = new PasswordUtil().generatePassword();
				String hashedPassword = passwordEncoder.encode(generatedPassword);
				user.setPassword(hashedPassword);
				if (userService.update(user)) {
					user.setPassword(generatedPassword);
					try {
						mailUtil.sendForgotPasswordEmail(user);
					} catch (Exception e) {
						cmsResponse.setSuccess(false);
						cmsResponse.setMessage("Password Reset Successfully! But Fails to send mail.");
						log.info("Password Reset Successfully! But Fails to send mail.");
						log.error(e.getMessage());
						return cmsResponse;
					}
					cmsResponse.setSuccess(true);
					log.info("Password Reset Successfully and Mail Sent to your EmailId");
					cmsResponse.setMessage("Password Reset Successfully and Mail Sent to your EmailId");
				} else {
					cmsResponse.setSuccess(false);
					log.info("Fails to Reset Password");
					cmsResponse.setMessage("Fails to Reset Password");
				}
			} else {
				cmsResponse.setSuccess(false);
				log.info("This EmailId Not Exist");
				cmsResponse.setMessage("This EmailId Not Exist");
			}
		} catch (Exception e) {
			cmsResponse.setSuccess(false);
			log.info("Something Went Wrong");
			cmsResponse.setMessage("Something Went Wrong");
			log.error(e.getMessage());
		}
		return cmsResponse;
	}
}
