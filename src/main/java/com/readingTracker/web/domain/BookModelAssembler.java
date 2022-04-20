package com.readingTracker.web.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Book;
import com.readingTracker.web.BookController;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
	@Override
	public EntityModel<Book> toModel(Book book) {
		return EntityModel.of(book, linkTo(methodOn(BookController.class).get(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).all()).withRel("books"));
	}
}
