package dropwizard.filmclubapp.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import java.security.Principal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import com.google.common.base.Optional;

import dropwizard.filmclubapp.resources.dto.LoginDTO;
import dropwizard.filmclubapp.resources.dto.TokenDTO;
import dropwizard.filmclubapp.auth.Secrets;
import dropwizard.filmclubapp.core.User;
import dropwizard.filmclubapp.dao.UserDAO;

import io.dropwizard.jersey.caching.CacheControl;

/**
 * Exchanges login information for a JWT token.
 *
 * @author Hendrik van Huyssteen
 * @since 09 Aug 2017
 */

@Path("auth")
public class AuthResource {
	
	private UserDAO dao;
	
	public AuthResource(UserDAO dao) {
		this.dao = dao;
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@Path("/login")
	@CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
	public final TokenDTO doLogin(@NotNull @Valid LoginDTO login) throws JoseException {
		Optional<User> user = dao.findByName(login.getUsername());
		if(user.isPresent()) {
			User loginUser = user.get();
			return new TokenDTO(buildToken(loginUser).getCompactSerialization(), null);
		} else {
			return new TokenDTO(null, "User doesn't exist");
		}
	}

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@Path("/register")
	public final TokenDTO doRegister(@NotNull @Valid LoginDTO login) throws JoseException {
		Optional<User> user = dao.findByName(login.getUsername());
		if(user.isPresent()) {
			return new TokenDTO(null, "User already exists");
		} else {
			User createdUser = dao.create(new User(login.getUsername(), login.getPassword()));
			return new TokenDTO(buildToken(createdUser).getCompactSerialization(), null);
			
		}
	}
	
	private JsonWebSignature buildToken(User user) {
		// These claims would be tightened up for production
		final JwtClaims claims = new JwtClaims();
		claims.setSubject("1");
		claims.setStringClaim("user", ((User) user).getName());
		claims.setIssuedAtToNow();
		claims.setGeneratedJwtId();

		final JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(HMAC_SHA256);
		jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));
		return jws;
	}
}
