package com.readingTracker.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingTracker.data.entity.Book;
import com.readingTracker.service.BookService;
import com.readingTracker.web.domain.BookModelAssembler;

@RestController @RequestMapping("/books")
public class BookController {
	private final BookService service;
	private final BookModelAssembler assembler;
	
	@Autowired
	public BookController(BookService service, BookModelAssembler bookModelAssembler) {
		this.service = service;
		this.assembler = bookModelAssembler;
	}
	
	@PostMapping("/save")
	ResponseEntity<EntityModel<Book>> save(@RequestBody Book newBook) {
		EntityModel<Book> entityModel = assembler.toModel(service.saveBook(newBook));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@GetMapping("{id}")
	public EntityModel<Book> get(@PathVariable Long id) {
		return assembler.toModel(service.findById(id));
	}
	
	@GetMapping("/all")
	public CollectionModel<EntityModel<Book>> all() {
		List<EntityModel<Book>> books = service.allBooks().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
	}
}
