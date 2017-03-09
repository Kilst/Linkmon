package com.linkmon.model.aonevonebattle.moves.status;

import com.linkmon.model.aonevonebattle.OneVStatusComponent;

public class NoStatus implements IStatusEffect {

	float chance = 1f;
	
	@Override
	public void setStatus(OneVStatusComponent statusComponent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getChance() {
		// TODO Auto-generated method stub
		return chance;
	}
	
	@Override 
	public String toString() {
		return "Empty";
	}
}
