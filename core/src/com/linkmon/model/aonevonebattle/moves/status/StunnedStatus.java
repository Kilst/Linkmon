package com.linkmon.model.aonevonebattle.moves.status;

import com.linkmon.model.aonevonebattle.OneVStatusComponent;

public class StunnedStatus implements IStatusEffect {

	float chance = 0.2f;
	
	@Override
	public void setStatus(OneVStatusComponent statusComponent) {
		// TODO Auto-generated method stub
		statusComponent.setStunned(true);
	}

	@Override
	public float getChance() {
		// TODO Auto-generated method stub
		return chance;
	}

	@Override 
	public String toString() {
		return "Atk Down";
	}
}
