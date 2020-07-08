package br.gov.ce.seduc.apimoviesrent.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.ce.seduc.apimoviesrent.model.enums.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( of = { "id" } )
@Entity
@Table( name = "tb_user" )
public class User {
	@Id
	@GeneratedValue( strategy = IDENTITY )
	private Long id;
	private String name;
	@Enumerated( EnumType.STRING )
	private UserRole role;
}
