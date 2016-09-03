package com.linkmon.componentmodel;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.IRenderingComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.eventmanager.EventManager;

public class World {
	
	private List<GameObject> objects;
	
	private List<GameObject> objectQueueAdd;
	private List<GameObject> objectQueueRemove;
	
	IRenderingComponent renderer;
	
	EventManager eManager;
	
	private float width;
	private float height;
	
	private boolean isLightOn = true;
	
	public World(EventManager eManager, float width, float height) {
		
		this.eManager = eManager;
		
		this.setWidth(width);
		this.setHeight(height);
		
		objects = new ArrayList<GameObject>();
		objectQueueAdd = new ArrayList<GameObject>();
		objectQueueRemove = new ArrayList<GameObject>();
	}
	
	public void addRenderer(IRenderingComponent renderer) {
		this.renderer = renderer;
	}
	
	public void addObjectToWorld(GameObject object) {
		if(!objects.contains(object)) {
			objectQueueAdd.add(object);
			object.addWorld(this);
		}
	}
	
	public void removeObjectFromWorld(GameObject object) {
		if(objects.contains(object)) {
			objectQueueRemove.add(object);
			Gdx.app.log("World", "Removed!");
		}
	}
	
	private void updateObjects() {
		for(GameObject object : objectQueueAdd) {
			objects.add(object);
		}
		objectQueueAdd.clear();
		
		for(GameObject object : objectQueueRemove) {
			objects.remove(object);
		}
		objectQueueRemove.clear();
	}
	
	public void update() {
		
		updateObjects();
		
		for(GameObject object : objects) {
			object.update(5);
		}
	}

	public List<GameObject> getObjects() {
		// TODO Auto-generated method stub
		return objects;
	}

	public EventManager geteManager() {
		// TODO Auto-generated method stub
		return eManager;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isLightOn() {
		return isLightOn;
	}

	public void setLightOn(boolean isLightOn) {
		this.isLightOn = isLightOn;
	}
}