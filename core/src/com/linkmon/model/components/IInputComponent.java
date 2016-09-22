package com.linkmon.model.components;

import com.linkmon.eventmanager.input.InputListener;
import com.linkmon.model.gameobject.GameObject;

public interface IInputComponent extends InputListener {
	public void update(GameObject object);
}
