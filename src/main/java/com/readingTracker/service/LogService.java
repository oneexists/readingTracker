package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Log;

public interface LogService {
	Log saveLog(Log log);
	Log findById(Long id);
	List<Log> getAllLogs();
	Log updateLog(Log log);
	void deleteLog(Long id);
}
