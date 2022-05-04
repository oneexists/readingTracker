package com.readingTracker.web.domain;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.readingTracker.data.entity.Log;
import com.readingTracker.data.entity.ReadingStatus;
import com.readingTracker.web.LogController;

@Component
public class LogModelAssembler implements RepresentationModelAssembler<Log, EntityModel<Log>> {
	@Override
	public EntityModel<Log> toModel(Log log) {
		EntityModel<Log> logModel = EntityModel.of(log, linkTo(methodOn(LogController.class).get(log.getId())).withSelfRel(),
				linkTo(methodOn(LogController.class).all()).withRel("logs"));
		if (log.getStatus() == ReadingStatus.IN_PROGRESS) {
			logModel.add(linkTo(methodOn(LogController.class).deleteLog(log.getId())).withRel("delete"));
			logModel.add(linkTo(methodOn(LogController.class).updateLog(log, log.getId())).withRel("update"));
		}
		if (log.getStatus() == ReadingStatus.FINISHED) {
			logModel.add(linkTo(methodOn(LogController.class).deleteLog(log.getId())).withRel("delete"));
		}
		return logModel;
	}
}
