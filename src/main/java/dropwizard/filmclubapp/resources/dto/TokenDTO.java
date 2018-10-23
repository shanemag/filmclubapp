package dropwizard.filmclubapp.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

	public TokenDTO(String token, String error) {
		this.token = token;
		this.error = error;
	}

	@JsonProperty("token")
	public String token;
	
	@JsonProperty("error")
	public String error;

}
