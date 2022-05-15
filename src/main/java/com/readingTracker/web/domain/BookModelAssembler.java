package com.readingTracker.web.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.readingTracker.web.BookController;
import com.readingTracker.web.dto.BookModel;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<BookModel, EntityModel<BookModel>> {
	@Override
	public EntityModel<BookModel> toModel(BookModel book) {
		return EntityModel.of(book, linkTo(methodOn(BookController.class).get(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).all()).withRel("books"));
	}

}
