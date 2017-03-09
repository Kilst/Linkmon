package com.linkmon.model.linkmon;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.input.InputEvent;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.components.IInputAction;
import com.linkmon.model.components.InputComponent;
import com.linkmon.model.gameobject.GameObject;

public class LinkmonInputComponent extends InputComponent {
	
	IInputAction action;

	public LinkmonInputComponent(EventManager eManager, GameObject object, IInputAction action) {
		super(eManager, object);
		// TODO Auto-generated constructor stub
		this.action = action;
	}

	@Override
	public void update(GameObject object) {
		super.update(object);
		// TODO Auto-generated method stub
		if(clicked) {
			if(action != null && ((BattleExtraComponent)object.getExtraComponents()) != null) {
				if(!((BattleExtraComponent)object.getExtraComponents()).getBattle().getBattleManager().isPlaying())
					action.doAction(object);
			}
			clicked = false;
		}
	}
	
}
