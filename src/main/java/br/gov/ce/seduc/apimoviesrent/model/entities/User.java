package br.gov.ce.seduc.apimoviesrent.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class User {
	@Id
	@GeneratedValue( strategy = IDENTITY )
	private Long id;
	private String name;
}
