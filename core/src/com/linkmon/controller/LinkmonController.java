package com.linkmon.controller;

import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.model.linkmon.LinkmonStatsComponent;
import com.linkmon.model.linkmon.LinkmonStatusComponent;
import com.linkmon.view.screens.DebuggingScreen;
import com.linkmon.view.screens.interfaces.ILinkmonAddedStats;
import com.linkmon.view.screens.interfaces.ILinkmonStats;

public class LinkmonController implements ScreenListener {
	
	private GameObject linkmon;
	
	public LinkmonController(GameObject linkmon) {
		this.linkmon = linkmon;
	}
	
	private void train(int statType) {
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStats().train(statType);
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addExhaustionLevel(-10);
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
				linkmon.getId(),
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
			case(ScreenEvents.DEBUGGING): {
				((DebuggingScreen)event.screen).updateLinkmonPosition(linkmon.getX(), linkmon.getY());
				return false;
			}
		}
		return false;
	}
}
