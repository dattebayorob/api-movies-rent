CREATE TABLE public.tb_category (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT tb_category_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_people (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT tb_people_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_user (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	"role" varchar(255) NULL,
	CONSTRAINT tb_user_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_movie (
	id bigserial NOT NULL,
	"name" varchar(255) NULL,
	quantity int4 NULL,
	director_id int8 NULL,
	screenwriter_id int8 NULL,
	CONSTRAINT tb_movie_pkey PRIMARY KEY (id),
	CONSTRAINT tb_movie_director_id_fkey FOREIGN KEY (director_id) REFERENCES tb_people(id),
	CONSTRAINT tb_movie_screenwriter_id_fkey FOREIGN KEY (screenwriter_id) REFERENCES tb_people(id)
);

CREATE TABLE public.tb_movie_cast (
	movie_id int8 NOT NULL,
	cast_id int8 NOT NULL,
	CONSTRAINT tb_movie_cast_pkey PRIMARY KEY (movie_id, cast_id),
	CONSTRAINT tb_movie_cast_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES tb_movie(id),
	CONSTRAINT tb_movie_cast_cast_id_fkey FOREIGN KEY (cast_id) REFERENCES tb_people(id)
);

CREATE TABLE public.tb_movie_category (
	movie_id int8 NOT NULL,
	category_id int8 NOT NULL,
	CONSTRAINT tb_movie_category_pkey PRIMARY KEY (category_id, movie_id),
	CONSTRAINT tb_movie_category_category_id_fkey FOREIGN KEY (category_id) REFERENCES tb_category(id),
	CONSTRAINT tb_movie_category_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES tb_movie(id)
);

CREATE TABLE public.tb_movie_rent (
	movie_id int8 NOT NULL,
	user_id int8 NOT NULL,
	rent_date timestamp NULL,
	return_date timestamp NULL,
	CONSTRAINT tb_movie_rent_pkey PRIMARY KEY (movie_id, user_id),
	CONSTRAINT tb_movie_rent_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES tb_movie(id),
  CONSTRAINT tb_movie_rent_user_id_fkey FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

INSERT INTO tb_user( id, name, role ) VALUES ( 1, 'administrator', 'ADMIN'), ( 2, 'user', 'USER');

INSERT INTO tb_category ( name ) 
  VALUES  ( 'Action' ), ('Animation'), ('Adventure'), ('Drama'), ('Documentary'), ('Short'), ('Comedy'), ('News'), 
          ('Romance'), ('Biography'), ('Family'), ('Talk-Show'), ('Thriller'), ('Fantasy'), ('Mistery'), ('Horror'),
          ('Sci-Fi'), ('Crime'), ('History'), ('Musical'),('Reality-TV'), ('Game-Show');

INSERT INTO tb_people ( id, "name" ) VALUES ( 1, 'Bong Joon Ho' ), ( 2, 'Song Kang-Ho'), ( 3, 'Woo-sik Choi'), (4,'Park So-Dam');

INSERT INTO tb_movie ( name, quantity, director_id , screenwriter_id ) VALUES ( 'Parasite', 5, 1, 1);

INSERT INTO tb_movie_cast ( cast_id, movie_id ) VALUES ( 2, 1), (3,1 ),(4,1);

INSERT INTO tb_movie_category (category_id , movie_id ) values( 7,1), (13, 1);

