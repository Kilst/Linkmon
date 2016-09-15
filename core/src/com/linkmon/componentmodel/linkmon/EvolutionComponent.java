package com.linkmon.componentmodel.linkmon;

import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;

public class EvolutionComponent implements IExtraComponents {

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)object.getExtraComponents()).getTimers().getEvolutionTimer().checkTimer()) {
			int newId = evolutionCheck(object);
			if(object.getId() != newId) {
				evolve(object, newId);
			}
		}
	}
	
	private void evolve(GameObject linkmon, int newId) {
		linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.EVOLVE, (int)linkmon.getId(), (int)newId));
		linkmon.setId(newId);
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().updateGrowthStage();
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getTimers().updateTimers(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getGrowthStage());
	}
	
	private int evolutionCheck(GameObject linkmon) {
		
		switch(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getGrowthStage()) {
			case(GrowthStages.BABY) : {
				return babyCheck(linkmon);
			}
			case(GrowthStages.ROOKIE) : {
				return rookieCheck(linkmon);
			}
		}
		
		return linkmon.getId();
	}
	
	private int babyCheck(GameObject linkmon) {
		switch(linkmon.getId()) {
			case(LinkmonIds.FIRE_BABY) : {
				return EvolutionConditions.fireBoy(linkmon);
			}
		}
		return linkmon.getId();
	}
	
	private int rookieCheck(GameObject linkmon) {
		switch(linkmon.getId()) {
			case(LinkmonIds.FIRE_BOY) : {
				return EvolutionConditions.fireChampion(linkmon);
			}
		}
		return linkmon.getId();
	}

}
