package com.readingTracker.data.entity;

public enum ReadingStatus {
	IN_PROGRESS("In Progress"), FINISHED("Finished"), DID_NOT_FINISH("Did Not Finish");

	private final String value;

	ReadingStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
