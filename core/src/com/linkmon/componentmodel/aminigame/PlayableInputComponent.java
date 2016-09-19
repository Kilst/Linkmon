package com.linkmon.componentmodel.aminigame;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.components.IInputComponent;
import com.linkmon.componentmodel.components.InputType;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;

public class PlayableInputComponent implements IInputComponent {
	
	private float veloX;
	private float veloY;
	
	public PlayableInputComponent(EventManager eManager) {
		eManager.addInputListener(this);
	}

	@Override
	public boolean onNotify(InputEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(InputType.BUTTON_EVENT) : {
				Gdx.app.log("PlayableInput", "Heard button press");
					veloX += 15;
			}
		}
		return false;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		((PlayablePhysicsComponent)object.getPhysicsComponent()).addVeloX(veloX);
		veloX = 0;
	}

}
