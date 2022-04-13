package com.readingTracker.web.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.AppUser;
import com.readingTracker.web.AppUserController;

@Component
public class AppUserModelAssembler implements RepresentationModelAssembler<AppUser, EntityModel<AppUser>> {

	@Override
	public EntityModel<AppUser> toModel(AppUser appUser) {
		return EntityModel.of(appUser, linkTo(methodOn(AppUserController.class).one(appUser.getUserId())).withSelfRel(),
				linkTo(methodOn(AppUserController.class).all()).withRel("users"));
	}

}
