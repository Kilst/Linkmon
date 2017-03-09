package com.linkmon.model.components;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.battles.BattleExtraComponent;
import com.linkmon.model.battles.BattleWorld;
import com.linkmon.model.gameobject.GameObject;

public class InputActionTarget implements IInputAction {

	@Override
	public void doAction(GameObject object) {
		// Set Target
		((BattleExtraComponent)object.getExtraComponents()).getBattle().setPlayerTarget(((BattleExtraComponent)object.getExtraComponents()).getBattleLinkmon().getTargetId());
		Gdx.app.log("INPUT ACTION TARGET", "Setting target");
	}

}
