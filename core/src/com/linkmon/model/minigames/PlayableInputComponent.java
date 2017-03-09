package com.linkmon.model.minigames;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.model.components.IInputComponent;
import com.linkmon.model.components.InputType;
import com.linkmon.model.gameobject.GameObject;

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
