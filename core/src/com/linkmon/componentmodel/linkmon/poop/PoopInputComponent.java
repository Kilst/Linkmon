package com.linkmon.componentmodel.linkmon.poop;

import com.linkmon.componentmodel.components.InputComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.EventManager;

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
