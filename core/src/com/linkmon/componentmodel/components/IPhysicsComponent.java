package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.model.gameobject.Direction;

public interface IPhysicsComponent {
	public void update(GameObject object);
	public void move(GameObject object);
	public void setMoveTo(float x, float y);
	public void setMoveToX(float x);
}
