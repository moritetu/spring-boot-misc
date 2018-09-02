package sample.web.form.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Integer id;
	
	@Getter @Setter
	private String name;
}
