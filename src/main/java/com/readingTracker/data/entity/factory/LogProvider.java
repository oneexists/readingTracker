package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.factory.impl.LogFactoryImpl;

public class LogProvider {
	public static LogFactory getFactory() {
		return new LogFactoryImpl();
	}
}
