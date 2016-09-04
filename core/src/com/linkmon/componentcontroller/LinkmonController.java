package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonStatsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.view.screens.interfaces.ILinkmonStats;

public class LinkmonController implements ScreenListener {
	
	private GameObject linkmon;
	
	public LinkmonController(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	public void feedLinkmon(int amount) {
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addHungerLevel(amount);
	}
	
	public void getStats(ILinkmonStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonStatusComponent status = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus();
		((ILinkmonStats)window).getLinkmonStats(
				stats.getHealth(),
				stats.getAttack(),
				stats.getDefense(),
				stats.getSpeed(),
				status.getCareMistakes(),
				status.getBirthDate(),
				stats.getRank()
				);
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
