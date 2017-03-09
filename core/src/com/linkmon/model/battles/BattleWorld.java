package com.linkmon.model.battles;

import java.util.Collections;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;

public class BattleWorld extends World {

	public BattleWorld(EventManager eManager, float width, float height) {
		super(eManager, width, height);
		// TODO Auto-generated constructor stub
	}

	public void zSortWorld() {
		// Sort list by y-axis
		Collections.sort(getObjects(), new ZComparator()); // Sort by Y axis
	}
	
	@Override
	public void update() {
		super.update();
//		zSortWorld();
	}
}
