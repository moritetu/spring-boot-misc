package sample.web.form.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
	
	final static Logger LOG = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping("/dashboard")
	public String index(
		@AuthenticationPrincipal UserDetails user,
		Model model) {
	
		// Authenticated information is included in SecurityContext.
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("userName", userName);
	
		model.addAttribute(user);
		
		// For debug dump
		LOG.debug(SecurityContextHolder.getContext().getAuthentication().toString());
	
		return "account/dashboard";
	}
}
