package dropwizard.filmclubapp.core;

import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Principal {
	 
	 private final String passHash;
	 
	 @Column(name = "display_name")
	 private final String userName;
	 
	 @ManyToMany(cascade = { 
		        CascadeType.PERSIST, 
		        CascadeType.MERGE
	 })
     @JoinTable(
         name = "members", 
         joinColumns = { @JoinColumn(name = "user_id") }, 
         inverseJoinColumns = { @JoinColumn(name = "club") }
     )
	 private Set<Club> clubs = new HashSet<>();
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	 
	 public User(String userName, String passHash) {
	     this.userName = userName;
	     this.passHash = passHash;
	 }
	 
     public String getName() {
        return userName;
     }
	 
     public long getId() {
        return id;
     }
     
     @Override
     public boolean equals(Object o) {
         if (this == o) {
             return true;
         }
         if (!(o instanceof User)) {
             return false;
         }

         final User that = (User) o;

         return Objects.equals(this.id, that.id) &&
                 Objects.equals(this.userName, that.userName) &&
                 Objects.equals(this.passHash, that.passHash);
     }
     
     public String getHash() {
		return passHash;
     }
}
