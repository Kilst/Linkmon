package com.linkmon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.view.screens.ScreenType;

public class LinkmonSprite extends GameSprite implements ViewListener {
	
	private EventManager eManager;
	private Sprite[] thoughtBubble;
	private int bubbleSwapCounter = -1;
	private Timer thoughtTimer;
	
	private Sprite deadSprite;
	private boolean dead = false;

	public LinkmonSprite(int id) {
		super(id);
		
	}
	
	public LinkmonSprite(int id, EventManager eManager1) {
		super(id);
		// TODO Auto-generated constructor stub
		this.eManager = eManager1;
		this.eManager.addViewListener(this);
		thoughtBubble = new Sprite[3];
		thoughtTimer = new Timer(2, true);
		thoughtTimer.start();
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ControllerEvent(ControllerEvents.CLICKED_LINKMON));
            }
		});
	}
	
	private void addThought(int thought) {
		if(thought == ViewEvents.UPDATE_HUNGRY)
			thoughtBubble[0] = new Sprite(ResourceLoader.getRegionFromId(4));
		if(thought == ViewEvents.UPDATE_POOP)
			thoughtBubble[1] = new Sprite(ResourceLoader.getRegionFromId(5));
		if(thought == ViewEvents.UPDATE_SLEEPY)
			thoughtBubble[2] = new Sprite(ResourceLoader.getRegionFromId(6)); // Sleepy
	}
	
	private void removeThought(int thought) {
		if(thought == ViewEvents.UPDATE_HUNGRY)
			thoughtBubble[0] = null; // Hungry
		if(thought == ViewEvents.UPDATE_POOP)
			thoughtBubble[1] = null; // Poopy
		if(thought == ViewEvents.UPDATE_SLEEPY)
			thoughtBubble[2] = null; // Sleepy
	}

	public void updateThoughtBubble() {
		if(thoughtTimer.checkTimer()) {
				bubbleSwapCounter++;
			while(bubbleSwapCounter < 3) {
				if(thoughtBubble[bubbleSwapCounter] != null)
					break;
				bubbleSwapCounter++;
			}
			
			if(bubbleSwapCounter == 3)
				bubbleSwapCounter = -1;
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha){
    	
		if(!dead) {
			if(eManager != null)
				updateThoughtBubble();
			
	    	currentFrame = anim.getCurrentFrame(Gdx.graphics.getDeltaTime());
	
			if(anim.isFlipped())
				batch.draw(currentFrame, getX()+currentFrame.getRegionWidth(), getY(), -currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
			else
				batch.draw(currentFrame, getX(), getY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
			
			if(bubbleSwapCounter != -1 && thoughtBubble[bubbleSwapCounter] != null)
	        	batch.draw(thoughtBubble[bubbleSwapCounter], getX()+(currentFrame.getRegionWidth()/1.3f), getY()+currentFrame.getRegionHeight()/1.2f,thoughtBubble[bubbleSwapCounter].getWidth(),thoughtBubble[bubbleSwapCounter].getHeight());
		}
		else
			batch.draw(deadSprite, getX(), getY(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ViewEvents.UPDATE_LINKMON_POSITION): {
				setPosition(event.x, event.y);
				break;
			}
			case(ViewEvents.UPDATE_LINKMON_ANIMATION): {
				this.anim.animationType = event.currentAnimation;
				if(event.direction == Direction.RIGHT)
					this.anim.flipped = true;
				else
					this.anim.flipped = false;
				break;
			}
			case(ViewEvents.UPDATE_HUNGRY): {
				if(event.status)
					addThought(ViewEvents.UPDATE_HUNGRY);
				else
					removeThought(ViewEvents.UPDATE_HUNGRY);
				break;
			}
			case(ViewEvents.UPDATE_SLEEPY): {
				if(event.status)
					addThought(ViewEvents.UPDATE_SLEEPY);
				else
					removeThought(ViewEvents.UPDATE_SLEEPY);
				break;
			}
			case(ViewEvents.UPDATE_POOP): {
				addThought(ViewEvents.UPDATE_POOP);
				break;
			}
			case(ViewEvents.REMOVE_POOP): {
				if(event.poopList.isEmpty())
					removeThought(ViewEvents.UPDATE_POOP);
				break;
			}
			case(ViewEvents.LINKMON_DEAD): {
				dead = true;
				deadSprite = new Sprite(ResourceLoader.getRegionFromId(1));
				break;
			}
		}
	}

}
