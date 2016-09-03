package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.components.IExtraComponents;
import com.linkmon.componentmodel.gameobject.GameObject;

public class ItemComponent implements IExtraComponents {
	
	private int quantity;
	private int price;
	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void add(int quantity) {
		this.quantity += quantity;
	}
	
	public void remove(int quantity) {
		this.quantity -= quantity;
		if(this.quantity < 1)
			this.quantity = 0;
	}

	public int getPrice() {
		return price;
	}
}
