package com.linkmon.model.linkmon.poop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.components.IExtraComponents;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;

public class PoopComponent implements IExtraComponents {
	
	private List<GameObject> poopList;
	private List<GameObject> poopListRemove;
	
	private long lastPooped  = System.nanoTime();
	
	private Timer timer;
	
	public PoopComponent() {
		poopList = new ArrayList<GameObject>();
		
		poopListRemove = new ArrayList<GameObject>();
		
		timer = new Timer(5, true);
		
		timer.start();
	}
	
	private void poopCheck(GameObject object) {
		if(timer.checkTimer() && poopList.size() < 3) {
			GameObject poop = ObjectFactory.getInstance().getObjectFromId(ObjectId.POOP);
			poop.addInputComponent(new PoopInputComponent(object.getWorld().geteManager(), poop));
			poop.setPosition(object.getX()+(object.getWidth()/2)-(poop.getWidth()/2), object.getY());
			poopList.add(poop);
			object.getWorld().addObjectToWorld(poop);
			
			lastPooped = System.nanoTime();
			
			Gdx.app.log("PoopComponent", "Pooped!");
		}
	}
	
	public void removePoop(GameObject poop) {
		poopListRemove.add(poop);
//		poop.getWorld().removeObjectFromWorld(poop);
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
		removePoops(object);
		
		poopCheck(object);
	}

	public List<GameObject> getPoopList() {
		// TODO Auto-generated method stub
		return poopList;
	}
	
	private void removePoops(GameObject object) {
		for(GameObject poop : poopList) {
			if(((PoopInputComponent)poop.getInputComponent()).isClicked()) {
				poopListRemove.add(poop);
			}
		}
		
		for(GameObject poop : poopListRemove) {
			object.getWorld().removeObjectFromWorld(poop);
			poop.getWorld().geteManager().removeInputListener(((PoopInputComponent)poop.getInputComponent()));
			poopList.remove(poop);
		}
		
		poopListRemove.clear();
	}
	
	public long getLastPooped() {
		return lastPooped;
	}

	public void setLastPooped(long lastPooped) {
		// TODO Auto-generated method stub
		this.lastPooped = lastPooped;
	}

	public void setPoopList(ArrayList<GameObject> poopList) {
		// TODO Auto-generated method stub
		this.poopList = poopList;
	}
}
