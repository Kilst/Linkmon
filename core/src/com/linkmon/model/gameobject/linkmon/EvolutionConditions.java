package com.linkmon.model.gameobject.linkmon;

public class EvolutionConditions {
	
	public static int fireBoy(Linkmon linkmon) {
//		if(System.nanoTime() - linkmon.getBirthDate().getNano() > 1800000000000L/12) // 30 mins(2.5mins)
		if(System.nanoTime() - linkmon.getBirthDate().getNano() > 1800000000000L) // 30 mins
			return LinkmonIds.FIRE_BOY;
		else
			return linkmon.getId();
	}
	
	public static int fireChampion(Linkmon linkmon) {
		if(System.nanoTime() - linkmon.getBirthDate().getNano() > 86400000000000L + 1800000000000L) { // 1 Day + 30mins
			if(linkmon.getStats().getAttack() > 10 && linkmon.getStats().getDefense() > 10) // 200 attack, 200 defense
				return LinkmonIds.FIRE_CHAMPION;
			else
				return linkmon.getId();
		}
		else
			return linkmon.getId();
	}

}
