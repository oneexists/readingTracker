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

import com.readingTracker.data.entity.Log;
import com.readingTracker.service.LogService;
import com.readingTracker.web.domain.LogModelAssembler;

@RestController @RequestMapping("/logs")
public class LogController {
	private final LogService service;
	private final LogModelAssembler assembler;
	@Autowired
	public LogController(LogService service, LogModelAssembler logModelAssembler) {
		this.service = service;
		this.assembler = logModelAssembler;
	}
	
	@PostMapping("/save")
	ResponseEntity<?> save(@RequestBody Log newLog) {
		EntityModel<Log> entityModel = assembler.toModel(service.saveLog(newLog));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@GetMapping("{id}")
	public EntityModel<Log> get(@PathVariable Long id) {
		return assembler.toModel(service.findById(id));
	}
	
	@GetMapping("/all")
	public CollectionModel<EntityModel<Log>> all() {
		List<EntityModel<Log>> logs = service.getAllLogs().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(logs, linkTo(methodOn(LogController.class).all()).withSelfRel());
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateLog(@RequestBody Log log, @PathVariable Long id) {
		EntityModel<Log> entityModel = assembler.toModel(service.updateLog(log));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteLog(@PathVariable Long id) {
		service.deleteLog(id);
		return ResponseEntity.noContent().build();
	}
}
