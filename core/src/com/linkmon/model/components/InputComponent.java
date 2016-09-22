package com.linkmon.model.components;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.model.MyVector2;
import com.linkmon.model.gameobject.GameObject;

public class InputComponent implements IInputComponent {
	
	protected boolean clicked = false;
	protected boolean dragged = false;
	
	protected MyVector2 position;
	
	protected boolean updated = false;
	
	protected EventManager eManager;
	
	GameObject object;
	
	public InputComponent(EventManager eManager, GameObject object) {
		eManager.addInputListener(this);
		this.object = object;
		
		this.eManager = eManager;
	}

	@Override
	public boolean onNotify(InputEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(InputType.CLICKED) : {
				if(object.getAabb().contains(event.getPosition())) {
					Gdx.app.log("InputComponent", "clicked!");
					clicked = true;
					updated = true;
					return true;
				}
			}
		}
		return false;
	}

	public void update(GameObject object) {
		// TODO Auto-generated method stub
		updated = false;
	}

	public boolean isClicked() {
		return clicked;
	}

	public boolean isDragged() {
		return dragged;
	}

}
