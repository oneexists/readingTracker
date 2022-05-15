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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingTracker.data.entity.Book;
import com.readingTracker.service.BookService;
import com.readingTracker.web.domain.BookConverter;
import com.readingTracker.web.domain.BookModelAssembler;
import com.readingTracker.web.dto.BookModel;

@RestController
@RequestMapping("/books")
public class BookController {
	private final BookService service;
	private final BookModelAssembler assembler;
	private final BookConverter converter;

	@Autowired
	public BookController(BookService service, BookModelAssembler bookModelAssembler, BookConverter converter) {
		this.service = service;
		this.assembler = bookModelAssembler;
		this.converter = converter;
	}

	@PostMapping("/save")
	ResponseEntity<EntityModel<BookModel>> save(@RequestBody Book newBook) {
		BookModel bookModel = converter.bookToModel(service.saveBook(newBook));
		EntityModel<BookModel> entityModel = assembler.toModel(bookModel);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@GetMapping("{id}")
	public EntityModel<BookModel> get(@PathVariable Long id) {
		return assembler.toModel(converter.bookToModel(service.findById(id)));
	}

	@GetMapping("/all")
	public CollectionModel<EntityModel<BookModel>> all() {
		List<BookModel> bookModelList = service.getAllBooks().stream().map(converter::bookToModel)
				.collect(Collectors.toList());
		List<EntityModel<BookModel>> books = bookModelList.stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateBook(@RequestBody Book updateBook, @PathVariable Long id) {
		EntityModel<BookModel> entityModel = assembler.toModel(converter.bookToModel(service.updateBook(updateBook)));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		service.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
