package com.linkmon.model.aonevonebattle.moves.status;

import com.linkmon.model.aonevonebattle.OneVStatusComponent;

public class SleepStatus implements IStatusEffect {

	private float chance = 1f;
	
	@Override
	public void setStatus(OneVStatusComponent statusComponent) {
		// TODO Auto-generated method stub
		
		statusComponent.setAsleep(true);
	}

	@Override
	public float getChance() {
		// TODO Auto-generated method stub
		return chance;
	}
	
	@Override 
	public String toString() {
		return "Sleep";
	}

}
