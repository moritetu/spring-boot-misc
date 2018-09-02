package sample.web.form.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityFormAuthenticationApplication {

	@Order(100)
	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		SecurityProperties securityProperties;
		
		/**
		 * 1. Called at first.
		 */
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// In case not use userdetail auto-configuration.
			User user = securityProperties.getUser();
			PasswordEncoder passwordEncorder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			
			InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userDetailsManager = auth.inMemoryAuthentication();
			
			
			// Register with UserDetailsBuilder
			userDetailsManager.withUser(user.getName()).password(passwordEncorder.encode(user.getPassword())).roles("ADMIN");
			userDetailsManager.withUser("guest").password(passwordEncorder.encode("guest")).roles("USER");
			
			// Register with UserDetails
			userDetailsManager.withUser(new org.springframework.security.core.userdetails.User(
					"anonymous", "{noop}anonymous", AuthorityUtils.createAuthorityList("ROLE_USER")));
		}

		/**
		 * 2. Called after configuration of AuthenticationManagerBuilder.
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Configure a new SecurityChainFilter.
			// Calling of authorizeRequests() sets AnyRequestMatcher to requestMatchar. 
			http
				.authorizeRequests()
				.antMatchers("/user/**")
				.authenticated()
					.and()
				.formLogin()
					.successForwardUrl("/account/dashboard")
					.permitAll();
		}

		/**
		 * 3. Called after configuration of HttpSecurity.
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
			// Configure a new SecurityChainFilter
			web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		}

	}

	/**
	 * Application entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityFormAuthenticationApplication.class, args);
	}
}
