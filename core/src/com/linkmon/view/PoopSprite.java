package com.linkmon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.linkmon.controller.LinkmonController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.poop.Poop;

public class PoopSprite extends Actor {
	
	private Sprite sprite;
	
	private Poop poop;
	
	private EventManager eManager;
	
	private PoopSprite poopSprite = this;
	
	public PoopSprite(Poop poop1, EventManager eManager) {
		this.eManager = eManager;
		this.poop = poop1;
		sprite = new Sprite(ResourceLoader.getRegionFromId(3));
		sprite.setScale(UIRenderer.scaleX, UIRenderer.scaleY);
		sprite.setY(45);
		this.setY(45);
		this.setX(poop.getX());
		sprite.setX(poop.getX());
		setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight()); // Needed for touch/click events to fire
		
		addListener();
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				String message = "[poop] Poop heard touchDown. " + getX();
		        Gdx.app.log("PoopSprite", message);
		        eManager.notify(new ControllerEvent(ControllerEvents.CLEAN_POOP, poop));
		        poopSprite.remove();
				return true;
			}
		});
	}

	public PoopSprite(float x, boolean save) {
		sprite = new Sprite(ResourceLoader.getRegionFromId(3));
		sprite.setScale(UIRenderer.scaleX, UIRenderer.scaleY);
		sprite.setY(45);
		this.setY(45);
		this.setX(x);
		sprite.setX(x);
		setBounds(sprite.getX(),sprite.getY(),sprite.getWidth()*UIRenderer.scaleX,sprite.getHeight()*UIRenderer.scaleY); // Needed for touch/click events to fire
		
		addListener();
	}

	@Override
    public void draw(Batch batch, float alpha){
        batch.draw(sprite, getX(), getY(),sprite.getWidth(),sprite.getHeight());
    }
}
