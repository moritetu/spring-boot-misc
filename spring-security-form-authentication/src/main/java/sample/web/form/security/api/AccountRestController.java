package sample.web.form.security.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sample.web.form.security.Account;
import sample.web.form.security.AccountService;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(path = "{id}")
	@ResponseBody
	public Account getAccount(@PathVariable int id) {
		Optional<Account> optional = Optional.ofNullable(accountService.findById(id));
		
		return optional.orElse(new Account());
	}
	
	@RequestMapping(path = "@{name}")
	@ResponseBody
	public Account getAccount(@PathVariable String name) {
		Optional<Account> optional = Optional.ofNullable(accountService.findByNamed(name));
		
		return optional.orElse(new Account());
	}
}
