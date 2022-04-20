package com.readingTracker.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import com.readingTracker.data.repository.AppUserRepository;
import com.readingTracker.web.domain.AppUserModelAssembler;
import com.readingTracker.data.entity.AppUser;

@RestController @RequestMapping("/users")
public class AppUserController {
	private final AppUserRepository repository;
	private final AppUserModelAssembler assembler;
	
	@Autowired
	public AppUserController(AppUserRepository appUserRepository, AppUserModelAssembler appUserModelAssembler) {
		this.repository = appUserRepository;
		this.assembler = appUserModelAssembler;
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody AppUser newUser) {
		EntityModel<AppUser> entityModel = assembler.toModel(repository.save(newUser));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@GetMapping("{id}")
	public EntityModel<AppUser> get(@PathVariable Long id) {
		AppUser user = repository.getById(id);
		return assembler.toModel(user);
	}
	
	@GetMapping("/all")
	public CollectionModel<EntityModel<AppUser>> all() {
		List<EntityModel<AppUser>> users = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(users, linkTo(methodOn(AppUserController.class).all()).withSelfRel());
	}

}