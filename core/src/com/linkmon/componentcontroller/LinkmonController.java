package com.linkmon.componentcontroller;

import com.badlogic.gdx.Screen;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.view.screens.interfaces.ILinkmonStats;

public class LinkmonController {
	
	private GameObject linkmon;
	
	public LinkmonController(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	public void addLinkmon(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	public void feedLinkmon(int amount) {
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addHungerLevel(amount);
	}

	public int[] getStats() {
		return null;
	}
	
	public int getCareMistakes() {
		return ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getCareMistakes();
	}
	
	public int getHungerLevel() {
		return ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getHungerLevel();
	}
	
	public void updateWindow(Screen window) {
		if(window instanceof ILinkmonStats) {
//			((ILinkmonStats)window).getLinkmonStats(
//						getStats().getHealth(),
//						getStats().getAttack(),
//						getStats().getDefense(),
//						getStats().getSpeed(),
//						getCareMistakes(),
//						getBirthDate(),
//						getRank()
//					);
		}
	}
}
