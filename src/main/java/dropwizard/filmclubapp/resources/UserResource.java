package dropwizard.filmclubapp.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;

import dropwizard.filmclubapp.core.User;
import dropwizard.filmclubapp.dao.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/users")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class UserResource {
	private UserDAO dao;
	
	public UserResource(UserDAO dao) {
		this.dao = dao;
	}
	
	@GET
	@Path("/{id}")
    @UnitOfWork
    public Optional<User> findById(@PathParam("id") Integer id) {
        return dao.findById(id);
    }
	
	@POST
    @UnitOfWork
    public User add(@Valid User user) {
        User newUser = dao.create(user);
        return newUser;
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public User update(@Valid User user) {
        dao.update(user);
        return user;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id) {
        dao.delete(dao.findById(id));
    }
}
