package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.input.InputListener;

public interface IInputComponent extends InputListener {
	public void update(GameObject object);
}
