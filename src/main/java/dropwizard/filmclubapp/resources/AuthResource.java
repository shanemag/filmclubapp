package dropwizard.filmclubapp.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import dropwizard.filmclubapp.resources.dto.LoginDTO;
import dropwizard.filmclubapp.resources.dto.TokenDTO;
import dropwizard.filmclubapp.FilmClvbConfiguration;
import dropwizard.filmclubapp.core.User;
import dropwizard.filmclubapp.dao.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.caching.CacheControl;

@Path("auth")
public class AuthResource {
	
	private UserDAO dao;
	private byte[] SECRET;
	
	public AuthResource(UserDAO dao, FilmClvbConfiguration conf) {
		this.dao = dao;
		this.SECRET = conf.getJwtTokenSecret().getBytes();
	}

	@POST
	@UnitOfWork
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
	@UnitOfWork
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
		jws.setKey(new HmacKey(SECRET));
		return jws;
	}
}
