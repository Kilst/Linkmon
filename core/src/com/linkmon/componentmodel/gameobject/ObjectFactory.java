package com.linkmon.componentmodel.gameobject;

public class ObjectFactory {
	
	private static IGameObjectFactory factory;
	
	// MUST initialize before use
	public static void init(IGameObjectFactory objectFactory) { // Pass in an object factory (based on framework choice)
		factory = objectFactory;
	}
	
	public static IGameObjectFactory getInstance() { // Returns the factory you initialized
		return factory;
	}

}
