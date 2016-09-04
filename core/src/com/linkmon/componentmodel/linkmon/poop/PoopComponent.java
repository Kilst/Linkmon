package com.linkmon.componentmodel.linkmon.poop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.helpers.Timer;

public class PoopComponent implements IExtraComponents {
	
	private List<GameObject> poopList;
	private List<GameObject> poopListRemove;
	
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
			
			Gdx.app.log("PoopComponent", "Pooped!");
		}
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
		poopCheck(object);
		
		for(GameObject poop : poopList) {
			if(((PoopInputComponent)poop.getInputComponent()).isClicked()) {
				poopListRemove.add(poop);
			}
		}
		
		for(GameObject poop : poopListRemove) {
			object.getWorld().removeObjectFromWorld(poop);
			poopList.remove(poop);
		}
		
		poopListRemove.clear();
	}

	public List<GameObject> getPoopList() {
		// TODO Auto-generated method stub
		return poopList;
	}
}
