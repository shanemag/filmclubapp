package dropwizard.filmclubapp;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class FilmClvbConfiguration extends Configuration {
	
	private static final String DATABASE = "database";
	
	@NotEmpty
	private String jwtTokenSecret;

	@JsonProperty
    public String getJwtTokenSecret() {
        return jwtTokenSecret;
    }

	@Valid
	@NotNull
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();

	@JsonProperty(DATABASE)
	public DataSourceFactory getDataSourceFactory() {
	  return dataSourceFactory;
	}

	@JsonProperty(DATABASE)
	public void setDataSourceFactory(final DataSourceFactory dataSourceFactory) {
	    this.dataSourceFactory = dataSourceFactory;
	}
	

}