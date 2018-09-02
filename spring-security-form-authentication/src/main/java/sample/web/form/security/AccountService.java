package sample.web.form.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class AccountService {	

	private final Map<Integer, Account> accountRepository = new ConcurrentHashMap<>();
	private final Map<String, Account> accountNameRepository = new ConcurrentHashMap<>();

	@PostConstruct
	public void loadInitialData() {
		registerAccount("admin", "guest", "anonymous");
	}
	
	private void registerAccount(String ...userNames) {
		int i = 0;
		for (String name: userNames) {
			Account account = new Account();
			account.setId(i++);
			account.setName(name);
	
			accountRepository.put(account.getId(), account);
			accountNameRepository.put(account.getName(), account);
		}
	}

	public Account findById(Integer id) {
		return accountRepository.get(id);
	}

	public Account findByNamed(String name) {
		return accountNameRepository.get(name);
	}
}
