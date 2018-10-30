package dropwizard.filmclubapp;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import dropwizard.filmclubapp.resources.AuthResource;
import dropwizard.filmclubapp.resources.ClubResource;
import dropwizard.filmclubapp.resources.ReviewResource;
import dropwizard.filmclubapp.resources.UserResource;

import java.security.Principal;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;

import dropwizard.filmclubapp.auth.JwtAuthenticator;
import dropwizard.filmclubapp.core.Club;
import dropwizard.filmclubapp.core.Item;
import dropwizard.filmclubapp.core.Review;
import dropwizard.filmclubapp.core.User;
import dropwizard.filmclubapp.dao.ClubDAO;
import dropwizard.filmclubapp.dao.ReviewDAO;
import dropwizard.filmclubapp.dao.UserDAO;

public class FilmClvbApplication extends Application<FilmClvbConfiguration> {
    public static void main(String[] args) throws Exception {
        new FilmClvbApplication().run(args);
    }
    
    private final HibernateBundle<FilmClvbConfiguration> hibernate = new HibernateBundle<FilmClvbConfiguration>
    (
    		User.class, Review.class, Club.class, Item.class
    ) 
    {
    @Override
        public DataSourceFactory getDataSourceFactory(FilmClvbConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<FilmClvbConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(FilmClvbConfiguration configuration, Environment environment) throws Exception {   
    	 registerResources(environment, configuration);
    	 registerAuthFilters(environment, configuration);
    }
    
    private void registerResources(Environment environment, FilmClvbConfiguration configuration) {
    	final UserDAO userdao = new UserDAO(hibernate.getSessionFactory());
    	final ReviewDAO reviewdao = new ReviewDAO(hibernate.getSessionFactory());
    	final ClubDAO clubdao = new ClubDAO(hibernate.getSessionFactory());
        environment.jersey().register(new ReviewResource(reviewdao));
        environment.jersey().register(new ClubResource(clubdao));
        environment.jersey().register(new AuthResource(userdao, configuration));
        environment.jersey().register(new UserResource(userdao));
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }
    
	private void registerAuthFilters(Environment environment, FilmClvbConfiguration configuration) throws Exception {
		 final byte[] key = configuration.getJwtTokenSecret().getBytes();;
		 final JwtConsumer consumer = new JwtConsumerBuilder()
            .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
            .setRequireExpirationTime() // the JWT must have an expiration time
            .setRequireSubject() // the JWT must have a subject claim
            .setVerificationKey(new HmacKey(key)) // verify the signature with the public key
            .setRelaxVerificationKeyValidation() // relaxes key length requirement
            .build(); // create the JwtConsumer instance

        environment.jersey().register(new AuthDynamicFeature(
            new JwtAuthFilter.Builder<User>()
                .setJwtConsumer(consumer)
                .setRealm("realm")
                .setPrefix("Bearer")
                .setAuthenticator(new JwtAuthenticator())
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        
    }
    

}