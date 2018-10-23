package dropwizard.filmclubapp.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {
	
	private String username;
	private String password;
	
	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@JsonProperty
	public String getUsername() {
		return username;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}
	
}
