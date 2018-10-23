package dropwizard.filmclubapp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dropwizard.filmclubapp.core.Club;
import dropwizard.filmclubapp.dao.ClubDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import jersey.repackaged.com.google.common.base.Optional;

@Path("/clubs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubResource {
	private ClubDAO dao;
	
	public ClubResource(ClubDAO dao) {
		this.dao = dao;
	}
    
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Club> findById(@PathParam("id") LongParam id) {
        return dao.findById(id.get());
    }
   
}
