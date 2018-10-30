package dropwizard.filmclubapp.auth;

import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;

import dropwizard.filmclubapp.core.User;
import io.dropwizard.auth.AuthFilter;

public class AuthFilterUtils {	

	public AuthFilter<JwtContext, User> buildJwtAuthFilter() {
		final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
				.setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

		return new JwtAuthFilter.Builder<User>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
				.setAuthenticator(new JwtAuthenticator()).buildAuthFilter();
	}

}
