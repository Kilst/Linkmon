package com.linkmon.model.battles;

import java.util.Comparator;

import com.linkmon.model.gameobject.GameObject;

public class SpeedComparator implements Comparator<GameObject> {

	// For sorting linkmon by speed
	
	@Override
	public int compare(GameObject arg0, GameObject arg1) {
		// TODO Auto-generated method stub
		return ((BattleExtraComponent)arg0.getExtraComponents()).getStats().getSpeed()-((BattleExtraComponent)arg1.getExtraComponents()).getStats().getSpeed();
	}

}
