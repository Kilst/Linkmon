package com.linkmon.model.linkmon.poop;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.components.InputComponent;
import com.linkmon.model.gameobject.GameObject;

public class PoopInputComponent extends InputComponent {

	public PoopInputComponent(EventManager eManager, GameObject object) {
		super(eManager, object);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(GameObject object) {
		
		if(clicked)
			eManager.removeInputListener(this);
		
		super.update(object);		
	}

	public void setClicked(boolean clicked) {
		// TODO Auto-generated method stub
		this.clicked = clicked;
	}
}
