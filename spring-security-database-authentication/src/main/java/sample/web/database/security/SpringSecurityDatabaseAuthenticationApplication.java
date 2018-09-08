package sample.web.database.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootApplication
public class SpringSecurityDatabaseAuthenticationApplication {

	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		UserDetailsManager userDetailsManager;
		
		@Autowired
		PasswordEncoder passwordEncoder;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// UserDetailServiceとPasswordEncorderを指定
			auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Configure a new SecurityChainFilter.
			// Calling of authorizeRequests() sets AnyRequestMatcher to requestMatchar. 
			http
				.authorizeRequests()
					// ROLE ADMINを要求
					.antMatchers("/user/**", "/h2-console/**").hasRole("ADMIN").and()
					// Form認証を要求
					.formLogin().defaultSuccessUrl("/user/home").and()
					// 以下は、H2 Consoleにアクセスするために必要
					.csrf().ignoringAntMatchers("/h2-console/**").and()
					.headers().frameOptions().sameOrigin();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			// Configure a new SecurityChainFilter
			web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		}

	}

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("admin")); // パスワード確認
		SpringApplication.run(SpringSecurityDatabaseAuthenticationApplication.class, args);
	}
}
