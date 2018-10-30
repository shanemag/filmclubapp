package dropwizard.filmclubapp.auth;


import java.util.Optional;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dropwizard.filmclubapp.core.User;
import io.dropwizard.auth.Authenticator;

public class JwtAuthenticator implements Authenticator<JwtContext, User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticator.class);

	/**
	 * Extracts user roles from Jwt. This method will be called once the token's signature has been verified.
	 */
	@Override
	public Optional<User> authenticate(JwtContext context) {
		try {
			JwtClaims claims = context.getJwtClaims();

			String username = claims.getSubject();
			String pass = (String) claims.getClaimValue("pass");

			return Optional.of(new User(username, pass));
		} catch (Exception e) {
			LOGGER.warn("msg=Failed to authorise user: {}", e.getMessage(), e);
			return Optional.empty();
		}
	}
}
