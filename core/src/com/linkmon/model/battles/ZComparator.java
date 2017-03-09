package com.linkmon.model.battles;

import java.util.Comparator;

import com.linkmon.model.gameobject.GameObject;

public class ZComparator implements Comparator<GameObject> {

	// For z-sorting
	
	@Override
	public int compare(GameObject arg0, GameObject arg1) {
		// TODO Auto-generated method stub
		return (int) (arg1.getY()-arg0.getY());
	}

}
