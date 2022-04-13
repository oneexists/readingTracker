package com.readingTracker.web.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Log;
import com.readingTracker.web.BookController;

@Component
public class LogModelAssembler implements RepresentationModelAssembler<Log, EntityModel<Log>> {
	@Override
	public EntityModel<Log> toModel(Log log) {
		return EntityModel.of(log, linkTo(methodOn(BookController.class).one(log.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).all()).withRel("books"));
	}
}
