//package dropwizard.filmclubapp.auth;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import dropwizard.filmclubapp.core.User;
//import io.dropwizard.auth.Authorizer;
//
///**
// * Determines if a user is authorised to access an API endpoint, after they were authenticated with {@link JwtAuthenticator}.
// *
// * See {@link com.hendrikvh.demos.jwtdemo.resources.ProtectedResourceOne}.
// *
// * @author Hendrik van Huyssteen
// * @since 21 Sep 2017
// */
//public class JwtAuthorizer implements Authorizer<User> {
//	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizer.class);
//
//	public boolean authorize(User user, String requiredPermission) {
//		if (user == null) {
//			LOGGER.warn("msg=user object was null");
//			return false;
//		}
//
//		String permissions = user.getPermissions();
//		if (permissions == null) {
//			LOGGER.warn("msg=roles were null, user={}, userId={}", user.getName(), user.getId());
//			return false;
//		}
//		return permissions.contains(requiredPermission);
//	}
//}