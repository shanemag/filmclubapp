package dropwizard.filmclubapp.resources;

import dropwizard.filmclubapp.core.Review;
import dropwizard.filmclubapp.dao.ReviewDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import jersey.repackaged.com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/review")
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {
	private ReviewDAO dao;
	
	public ReviewResource(ReviewDAO dao) {
		this.dao = dao;
	}
    
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Review> findById(@PathParam("id") LongParam id) {
        return dao.findById(id.get());
    }
}
