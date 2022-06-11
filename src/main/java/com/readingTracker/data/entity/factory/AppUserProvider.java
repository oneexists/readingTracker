package com.readingTracker.data.entity.factory;

import com.readingTracker.data.entity.factory.impl.AppUserFactoryImpl;

public class AppUserProvider {
	public static AppUserFactory getFactory() {
		return new AppUserFactoryImpl();
	}
}
