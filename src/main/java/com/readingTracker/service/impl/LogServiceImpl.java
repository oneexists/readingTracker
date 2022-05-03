package com.readingTracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingTracker.data.entity.Log;
import com.readingTracker.data.repository.LogRepository;
import com.readingTracker.service.LogService;

@Service @Transactional
public class LogServiceImpl implements LogService {
	private final LogRepository repository;
	
	@Autowired
	public LogServiceImpl(LogRepository repository) {
		this.repository = repository;
	}

	@Override
	public Log saveLog(Log log) {
		return repository.save(log);
	}

	@Override
	public Log findById(Long id) {
		return repository.getById(id);
	}

	@Override
	public List<Log> getAllLogs() {
		return repository.findAll();
	}
	
	@Override
	public Log updateLog(Log log) {
		return repository.findById(log.getId())
				.map(updateLog -> {
					updateLog.setBook(log.getBook());
					updateLog.setStart(log.getStart());
					updateLog.setFinish(log.getFinish());
					updateLog.setUser(log.getUser());
					return repository.saveAndFlush(updateLog);
				})
				.orElseGet(() -> {
					return repository.save(log);
				});
	}
	
	@Override
	public void deleteLog(Long id) {
		repository.deleteById(id);
	}
}
