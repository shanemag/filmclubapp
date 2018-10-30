
package dropwizard.filmclubapp.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import dropwizard.filmclubapp.core.User;
import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {
	
	public UserDAO(SessionFactory factory) {
        super(factory);
    }
	
	public Optional<User> findById(Integer id) {
        return Optional.ofNullable(get(id));
    }
	
	public Optional<List<User>> getAll() {
			
		  CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		  CriteriaQuery<User> cq = cb.createQuery(User.class);
		  Root<User> root = cq.from(User.class);
		  cq.select(root);
		   
		  Query<User> query = currentSession().createQuery(cq);
		  List<User> results = query.getResultList();
	      return Optional.ofNullable(results);
	}
	
	public Optional<User> findByName(String username) {
		
		  CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		  CriteriaQuery<User> cq = cb.createQuery(User.class);
		  Root<User> root = cq.from(User.class);
		  cq.select(root).where(cb.equal(root.get("userName"), username));
		   
		  Query<User> query = currentSession().createQuery(cq);
		  List<User> results = query.getResultList();
		  if(results.isEmpty()) {
			  return Optional.empty();
		  } else {
			  return Optional.ofNullable(results.get(0));
		  }
    }

    public User create(User user) {
        return persist(user);
    }
    
    public void delete(Optional<User> optional) {
        currentSession().delete(optional);
    }
    
    public void update(User user) {
        currentSession().saveOrUpdate(user);
    }
    
}
