package com.linkmon.componentmodel.linkmon;

import com.linkmon.componentmodel.gameobject.GameObject;

public class EvolutionConditions {
	
	public static int fireBoy(GameObject linkmon) {
//		if(System.nanoTime() - linkmon.getBirthDate().getNano() > 1800000000000L/12) // 30 mins(2.5mins)
		if(System.nanoTime() - ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getBirthDate().getNano() > 1800000000000L) // 30 mins
			return LinkmonIds.FIRE_BOY;
		else
			return linkmon.getId();
	}
	
	public static int fireChampion(GameObject linkmon) {
		if(System.nanoTime() - ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getBirthDate().getNano() > 86400000000000L + 1800000000000L) { // 1 Day + 30mins
			if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats().getAttack() > 10 
					&& ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats().getDefense() > 10)
				return LinkmonIds.FIRE_CHAMPION;
			else
				return linkmon.getId();
		}
		else
			return linkmon.getId();
	}

}
