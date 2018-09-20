package com.example.sample.jpa;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import lombok.Getter;
import lombok.Setter;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Setter
	@Getter
	private String userName;

	public AuditorAwareImpl(String userName) {
		this.userName = userName;
	}
	
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable(userName);
	}
}
