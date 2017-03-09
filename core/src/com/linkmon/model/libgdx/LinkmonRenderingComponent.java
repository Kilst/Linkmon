package com.linkmon.model.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class LinkmonRenderingComponent extends LibgdxRenderingComponent {
	
	private Sprite[] thoughtBubble;
	private int bubbleSwapCounter = -1;
	private Timer thoughtTimer;
	
	public LinkmonRenderingComponent() {
		thoughtBubble = new Sprite[3];
		thoughtTimer = new Timer(2, true);
		thoughtTimer.start();
	}
	
	@Override
	public void update(GameObject linkmon) {
		// TODO Auto-generated method stub
		super.update(linkmon);
		
		checkThoughts(linkmon);
		
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
	
	private void checkThoughts(GameObject linkmon) {
		try {
			if(((LinkmonExtraComponents)linkmon.getExtraComponents()) == null)
				return;
			if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isHungry()) {
				if(thoughtBubble[0] == null)
					thoughtBubble[0] = new Sprite(ResourceLoader.getRegionFromId(4));
			}
			else
				thoughtBubble[0] = null;
			if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getPoopComponent().getPoopList().size() > 0) {
				if(thoughtBubble[1] == null)
					thoughtBubble[1] = new Sprite(ResourceLoader.getRegionFromId(5));
			}
			else
				thoughtBubble[1] = null;
			if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isSleepy()) {
				if(thoughtBubble[2] == null)
					thoughtBubble[2] = new Sprite(ResourceLoader.getRegionFromId(6)); // Sleepy
			}
			else
				thoughtBubble[2] = null;
		}
		catch(Exception e) {
			
		}
	}
	
	@Override
	public void draw(Batch batch, GameObject object) {
		super.draw(batch, object);
		
		updateThoughtBubble();
		
		if(bubbleSwapCounter != -1 && thoughtBubble[bubbleSwapCounter] != null)
        	batch.draw(thoughtBubble[bubbleSwapCounter], object.getX()+(object.getWidth()/1.3f), object.getY()+object.getHeight()/1.2f,thoughtBubble[bubbleSwapCounter].getWidth(),thoughtBubble[bubbleSwapCounter].getHeight());
	}

}
