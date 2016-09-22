package com.linkmon.model.components;

import java.util.List;

import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.GameObject;

public interface IPhysicsComponent {
	public void update(GameObject object, List<GameObject> objects);
	public void move(GameObject object);
	public void setMoveTo(float x, float y);
	public void setMoveToX(float x);
}
