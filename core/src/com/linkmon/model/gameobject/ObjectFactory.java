package com.linkmon.model.gameobject;

import com.linkmon.eventmanager.EventManager;

public class ObjectFactory {
	
	private static IGameObjectFactory factory;
	
	// MUST initialize before use
	public static void init(IGameObjectFactory objectFactory, EventManager eManager) { // Pass in an object factory (based on framework choice)
		factory = objectFactory;
		factory.setEventManager(eManager);
	}
	
	public static IGameObjectFactory getInstance() { // Returns the factory you initialized
		return factory;
	}

}
