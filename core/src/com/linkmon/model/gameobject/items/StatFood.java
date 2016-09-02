package com.linkmon.model.gameobject.items;

import com.linkmon.model.gameobject.linkmon.Linkmon;
import com.linkmon.model.gameobject.linkmon.StatType;

public class StatFood extends Food {
	
	private int statType;
	private int statAmount;

	public StatFood(int id, String name, int price, int quantity, int feedAmount, int hungerFactor, int statType, int statAmount) {
		super(id, name, price, quantity, feedAmount, hungerFactor);
		// TODO Auto-generated constructor stub
		this.statType = statType;
		this.statAmount = statAmount;
	}
	
	@Override
	public boolean useItem(Object linkmon) {
		// TODO Auto-generated method stub
		if(((Linkmon)linkmon).feed(getFeedAmount(), getHungerFactor())) {
			switch(statType) {
				case (StatType.HEALTH) : {
					((Linkmon)linkmon).getStats().setHealth(((Linkmon)linkmon).getStats().getHealth()+statAmount);
					break;
				}
				case (StatType.ATTACK) : {
					((Linkmon)linkmon).getStats().setAttack(((Linkmon)linkmon).getStats().getAttack()+statAmount);
					break;
				}
				case (StatType.DEFENSE) : {
					((Linkmon)linkmon).getStats().setDefense(((Linkmon)linkmon).getStats().getDefense()+statAmount);
					break;
				}
				case (StatType.SPEED) : {
					((Linkmon)linkmon).getStats().setSpeed(((Linkmon)linkmon).getStats().getSpeed()+statAmount);
					break;
				}
			}
			return true;
		}
		else
			return false;
	}

	public int getStatType() {
		// TODO Auto-generated method stub
		return statType;
	}

}
