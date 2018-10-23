package dropwizard.filmclubapp;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class FilmClvbConfiguration extends Configuration {
	
	private static final String DATABASE = "database";
	private String jwtTokenSecret = "dfwzsdzwh823zebdwdz772632gdsbd";

    public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
        return jwtTokenSecret.getBytes("UTF-8");
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