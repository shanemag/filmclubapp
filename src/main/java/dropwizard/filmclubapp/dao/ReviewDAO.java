package dropwizard.filmclubapp.dao;

import org.hibernate.SessionFactory;

import dropwizard.filmclubapp.core.Review;
import io.dropwizard.hibernate.AbstractDAO;
import jersey.repackaged.com.google.common.base.Optional;

public class ReviewDAO extends AbstractDAO<Review> {
	public ReviewDAO(SessionFactory factory) {
        super(factory);
    }
	
	public Optional<Review> findById(Long id) {
        return Optional.fromNullable(get(id));
    }
 
    public Review create(Review review) {
        return persist(review);
    }
    
}
