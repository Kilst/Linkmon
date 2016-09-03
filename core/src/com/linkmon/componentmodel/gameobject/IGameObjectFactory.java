package com.linkmon.componentmodel.gameobject;

public interface IGameObjectFactory {
	public GameObject createLinkmon(int id);
	public GameObject getObjectFromId(int id);
}
