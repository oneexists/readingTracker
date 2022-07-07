package com.readingTracker.service;

import java.util.List;

import com.readingTracker.data.entity.Log;

/**
 * @author skylar
 *
 */
public interface LogService {
	/**
	 * Save a new log
	 * 
	 * @param log the log to save
	 * @return log the saved log
	 */
	Log saveLog(Log log);

	/**
	 * Find log by id
	 * 
	 * @param id the id of the log
	 * @return log the log with the same id
	 */
	Log findById(Long id);

	/**
	 * Find all logs
	 * 
	 * @return logs the list of logs
	 */
	List<Log> getAllLogs();

	/**
	 * Update a log
	 * 
	 * @param log the new log
	 * @return log the updated log
	 */
	Log updateLog(Log log);

	/**
	 * Delete the log by id
	 * 
	 * @param id the id of the log
	 */
	void deleteLog(Long id);

}
