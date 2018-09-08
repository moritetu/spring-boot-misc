package sample.web.database.security;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class UserForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 64)
	@Setter @Getter
	private String username;
	
	@NotNull
	@Setter @Getter
	@Size(min = 6, max = 128)
	private String password;
	
}
