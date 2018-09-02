package sample.web.form.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {

	final static Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

}
