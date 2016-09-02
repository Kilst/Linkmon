package com.linkmon.model.gameobject.items;

import com.linkmon.model.gameobject.linkmon.Linkmon;

public class Food extends Item {
	
	private int feedAmount;
	
	private int hungerFactor; // Higher means hunger slows down
	
	public Food(int id, String name, int price, int quantity, int feedAmount, int hungerFactor) {
		super(id, name, price, quantity);
		this.feedAmount = feedAmount;
		this.hungerFactor = hungerFactor;
	}

	@Override
	public boolean useItem(Object linkmon) {
		// TODO Auto-generated method stub
		return ((Linkmon)linkmon).feed(feedAmount, hungerFactor);
	}

	public int getFeedAmount() {
		// TODO Auto-generated method stub
		return feedAmount;
	}
	
	public int getHungerFactor() {
		// TODO Auto-generated method stub
		return hungerFactor;
	}
}
