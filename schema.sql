DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  display_name VARCHAR (100) NOT NULL
);

DROP TABLE IF EXISTS session;
CREATE TABLE session (
  access_token varchar(23) PRIMARY KEY,
  user_id integer REFERENCES users,
  created timestamp NOT NULL
);

DROP TABLE IF EXISTS clubs CASCADE;
CREATE TABLE clubs (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(100) NOT NULL,
  type varchar(20) NOT NULL
);

DROP TABLE IF EXISTS members;
CREATE TABLE members (
  user_id integer REFERENCES users,
  club integer REFERENCES clubs,
  admin boolean NOT NULL,
  PRIMARY KEY (user_id, club)
);

DROP TABLE IF EXISTS items CASCADE;
CREATE TABLE items (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  club integer REFERENCES clubs,
  title varchar(200) NOT NULL,
  type varchar (20) NOT NULL
);

DROP TABLE IF EXISTS reviews;
CREATE TABLE reviews (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  user_id integer REFERENCES users,
  item integer REFERENCES items,
  club integer REFERENCES clubs,
  content  text NOT NULL
);