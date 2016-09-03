package com.linkmon.componentmodel.gameobject;

public class ObjectFactory {
	
	private static IGameObjectFactory factory;
	
	// Must initialize before use
	public static void init(IGameObjectFactory objectFactory) {
		factory = objectFactory;
	}
	
	public static IGameObjectFactory getInstance() {
		return factory;
	}

}
