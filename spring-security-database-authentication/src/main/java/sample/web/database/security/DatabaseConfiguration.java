package sample.web.database.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;


@Configuration
public class DatabaseConfiguration {

	@Autowired
	DataSource dataSource;
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// デフォルトはbcrypt
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
