package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonStatsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.gameobject.linkmon.StatType;
import com.linkmon.view.screens.interfaces.ILinkmonAddedStats;
import com.linkmon.view.screens.interfaces.ILinkmonStats;

public class LinkmonController implements ScreenListener {
	
	private GameObject linkmon;
	
	public LinkmonController(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	private void train(int statType) {
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats().train(statType);
	}
	
	// Screen Updates
	
	public void getLinkmonAddedStats(ILinkmonAddedStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		window.getAddedStats(
				stats.getAddedHealth(),
				stats.getAddedAttack(),
				stats.getAddedDefense(),
				stats.getAddedSpeed()
				);
	}
	
	public void getLinkmonStats(ILinkmonStats window) {
		LinkmonStatsComponent stats = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats();
		LinkmonStatusComponent status = ((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus();
		window.getLinkmonStats(
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
		switch(event.eventId) {
			case(ScreenEvents.TRAIN_LINKMON): {
				train(event.value);
				return false;
			}
			case(ScreenEvents.GET_LINKMON_STATS): {
				getLinkmonStats((ILinkmonStats) event.screen);
				return false;
			}
			case(ScreenEvents.GET_LINKMON_ADDED_STATS): {
				getLinkmonAddedStats((ILinkmonAddedStats) event.screen);
				return false;
			}
		}
		return false;
	}
}
