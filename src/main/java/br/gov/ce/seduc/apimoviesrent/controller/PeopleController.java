package br.gov.ce.seduc.apimoviesrent.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.seduc.apimoviesrent.model.dtos.LabelDTO;
import br.gov.ce.seduc.apimoviesrent.service.PeopleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/peoples")
@RequiredArgsConstructor
public class PeopleController {
	private final PeopleService peopleService;
	
	@GetMapping
	public Set<LabelDTO> findbyName(@RequestParam(name = "name", required=false) String name ) {
		return peopleService.findByNameLike(name);
	}
}
