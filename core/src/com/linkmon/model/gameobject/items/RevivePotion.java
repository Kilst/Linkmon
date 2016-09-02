package com.linkmon.model.gameobject.items;

import com.linkmon.model.gameobject.linkmon.Linkmon;

public class RevivePotion extends Item {

	public RevivePotion(int id, String name, int price, int quantity) {
		super(id, name, price, quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean useItem(Object object) {
		// TODO Auto-generated method stub
		Linkmon linkmon = (Linkmon)object;
		if(!linkmon.isDead())
			return false;
		linkmon.setDead(false);
		linkmon.setExhausted(false);
		linkmon.setExhaustionLevel(100);
		linkmon.setHungry(false);
		linkmon.setHungerLevel(100);
		linkmon.getPoopList().clear();
		linkmon.setSick(false);
		
		return true;
		
	}
}
