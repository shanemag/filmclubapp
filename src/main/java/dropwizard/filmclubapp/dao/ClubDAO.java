package dropwizard.filmclubapp.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import dropwizard.filmclubapp.core.Club;
import io.dropwizard.hibernate.AbstractDAO;
import jersey.repackaged.com.google.common.base.Optional;

public class ClubDAO extends AbstractDAO<Club> {
	public ClubDAO(SessionFactory factory) {
        super(factory);
    }
	
	public Optional<Club> findById(Long id) {
        return Optional.fromNullable(get(id));
    }
 
    public Club create(Club club) {
        return persist(club);
    }
    
    public List<Club> findAll() {
        return list(namedQuery("dropwizard.filmclubapp.core.Club.findAll"));
    }
}
