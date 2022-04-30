package com.readingTracker.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.readingTracker.service.AppUserService;
import com.readingTracker.web.domain.AppUserModelAssembler;
import com.readingTracker.data.entity.AppUser;

@RestController @RequestMapping("/users")
public class AppUserController {
	private final AppUserService service;
	private final AppUserModelAssembler assembler;
	
	@Autowired
	public AppUserController(AppUserService service, AppUserModelAssembler appUserModelAssembler) {
		this.service = service;
		this.assembler = appUserModelAssembler;
	}
	
	@GetMapping("/all")
	public CollectionModel<EntityModel<AppUser>> all() {
		List<EntityModel<AppUser>> users = service.getAllUsers().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(users, linkTo(methodOn(AppUserController.class).all()).withSelfRel());
	}
	
	@PostMapping("/save")
	public ResponseEntity<EntityModel<AppUser>> save(@RequestBody AppUser newUser) {
		EntityModel<AppUser> entityModel = assembler.toModel(service.saveUser(newUser));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@GetMapping("{id}")
	public EntityModel<AppUser> get(@PathVariable Long id) {
		AppUser user = service.getUser(id);
		return assembler.toModel(user);
	}

	@PostMapping("/update")
	public ResponseEntity<EntityModel<AppUser>> update(@RequestBody AppUser appUser) {
		EntityModel<AppUser> entityModel = assembler.toModel(service.updateUser(appUser));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}