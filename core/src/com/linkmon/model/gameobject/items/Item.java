package com.linkmon.model.gameobject.items;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.linkmon.Linkmon;

public abstract class Item extends GameObject {
	
	private int quantity;
	
	private int price;
	
	private String name;

	public Item(int id, String name, int price, int quantity) {
		super(id);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public abstract boolean useItem(Object target);

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void add(int quantity) {
		this.quantity += quantity;
	}
	
	public void remove() {
		this.quantity -= 1;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
