package com.linkmon.model.linkmon.poop;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.model.components.InputComponent;
import com.linkmon.model.components.InputType;
import com.linkmon.model.gameobject.GameObject;

public class PoopInputComponent extends InputComponent {
	
	private GameObject poopObject;
	private PoopComponent poopComponent;

	public PoopInputComponent(EventManager eManager, GameObject object, PoopComponent poopComponent) {
		super(eManager, object);
		// TODO Auto-generated constructor stub
		this.poopComponent = poopComponent;
		poopObject = object;
	}

	@Override
	public void update(GameObject object) {
		
		super.update(object);		
	}
	
	@Override
	public boolean onNotify(InputEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(InputType.CLICKED) : {
				if(poopObject.getWorld().getActive()) { // Check if GameWorld is active (blocks clicks from other worlds)
					if(poopObject.getAabb().contains(event.getPosition())) { // aabb check against click position
						eManager.removeInputListener(this); // remove input listener
						poopComponent.removePoop(poopObject); // remove object from world
						return true;
					}
				}
					
			}
		}
		return false;
	}

	public void setClicked(boolean clicked) {
		// TODO Auto-generated method stub
		this.clicked = clicked;
	}
}
