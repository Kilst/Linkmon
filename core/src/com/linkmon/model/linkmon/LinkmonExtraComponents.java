package com.linkmon.model.linkmon;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.poop.PoopComponent;

public class LinkmonExtraComponents implements IExtraComponents {
	
	private LinkmonTimerComponent timerComponent;
	private LinkmonStatsComponent statsComponent;
	private LinkmonStatusComponent statusComponent;
	private EvolutionComponent evolutionComponent;
	private PoopComponent poopComponent;
	
	public LinkmonExtraComponents() {
		timerComponent = new LinkmonTimerComponent();
		statsComponent = new LinkmonStatsComponent();
		statusComponent = new LinkmonStatusComponent();
		evolutionComponent = new EvolutionComponent();
		poopComponent = new PoopComponent();
	}
	
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		timerComponent.update(object);
		statsComponent.update(object);
		statusComponent.update(object);
		evolutionComponent.update(object);
		poopComponent.update(object);
		
//		Gdx.app.log("LinkmonExtraComponents", "Fired");
	}
	
	public LinkmonTimerComponent getTimers() {
		return timerComponent;
	}

	public LinkmonStatsComponent getStats() {
		return statsComponent;
	}
	public LinkmonStatusComponent getStatus() {
		return statusComponent;
	}

	public PoopComponent getPoopComponent() {
		return poopComponent;
	}

}
