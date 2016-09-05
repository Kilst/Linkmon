package com.linkmon.componentmodel;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.componentmodel.libgdx.LibgdxRenderingComponent;
import com.linkmon.componentmodel.items.ItemComponent;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;
import com.linkmon.componentmodel.linkmon.LinkmonInputComponent;
import com.linkmon.componentmodel.linkmon.LinkmonPhysicsComponent;
import com.linkmon.componentmodel.linkmon.LinkmonStatusComponent;
import com.linkmon.componentmodel.linkmon.LinkmonTimerComponent;
import com.linkmon.componentmodel.linkmon.poop.PoopInputComponent;
import com.linkmon.eventmanager.EventManager;

public class Player {
	
	private GameObject linkmon;
	
	private int gold;
	private String name;
	
	private List<GameObject> items;
	
	private EventManager eManager;
	
	public Player(EventManager eManager, int eggChoice) {
		linkmon = ObjectFactory.getInstance().createLinkmon(eggChoice);
		
		gold = 15000;
		
		name = "Kilst";
		
		items = new ArrayList<GameObject>();
		
		this.eManager = eManager;
		
		addItem(ObjectId.MEAT, 1);
	}

	public GameObject getLinkmon() {
		// TODO Auto-generated method stub
		return linkmon;
	}
	
	public void addItem(int itemId, int quantity) {
		GameObject item = ObjectFactory.getInstance().getObjectFromId(itemId);
		if(items.contains(item))
			((ItemComponent)item.getExtraComponents()).add(quantity);
		else
			items.add(item);
	}

	public void addGold(int amount) {
		// TODO Auto-generated method stub
		gold += amount;		
	}

	public int getGold() {
		// TODO Auto-generated method stub
		return gold;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public List<GameObject> getItems() {
		// TODO Auto-generated method stub
		return items;
	}

}
