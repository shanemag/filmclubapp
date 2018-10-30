package dropwizard.filmclubapp.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {
	
	private String username;
	private String password;
	
	@JsonProperty
	public String getUsername() {
		return username;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}
	
}
