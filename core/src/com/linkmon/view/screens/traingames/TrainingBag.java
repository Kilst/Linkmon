package com.linkmon.view.screens.traingames;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.helpers.ResourceLoader;

public class TrainingBag extends Actor {
	
	private Sprite sprite;
	
	private int health = 20;
	
	private float veloX;
	private float veloY;
	
	private float maxVelo = 15;
	
	private float friction = 0.03f;
	
	private float width;
	private float height;
	
	private float boundsWidth;
	private float boundsHeight;
	
	public TrainingBag(float width, float height) {
		sprite = new Sprite(ResourceLoader.getRegionFromId(8));
		this.width = width;
		this.height = height;
		this.setBounds(getX(), getY(), sprite.getWidth()*3f, sprite.getHeight()*1.5f);
		
		boundsWidth = sprite.getWidth()*3f;
		boundsHeight = sprite.getHeight()*1.5f;
		
		this.debug();
		
		this.addListener(new ClickListener(){
            
            @Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
//            	Gdx.app.log("TrainingBag", "Clicked!");
            	Random rnd = new Random();
            	int direction = rnd.nextInt(2);
            	if(direction == 0)
            		direction = -1;
            	veloX+=(rnd.nextInt(5) + 3)*direction;
            	
            	direction = rnd.nextInt(2);
            	if(direction == 0)
            		direction = -1;
            	veloY+=(rnd.nextInt(5) + 3)*direction;
            	
            	health -= 1;
				return true;
			}
		});
	}
	
	private void outOfBounds() {
		if(getX() < 1 || getX() + boundsWidth >= width) {
			veloX = -veloX;
			if(getX() < 1)
				setX(1);
			else
				setX(width - boundsWidth);
		}
		
		if(getY() < 1  || getY() + boundsHeight >= height) {
			veloY = -veloY;
			if(getY() < 1)
				setY(1);
			else
				setY(height - boundsHeight);
		}
	}
	
	public void update() {
		
		if(veloX >= 0) {
			veloX -= friction;
			if(veloX >= maxVelo)
				veloX = maxVelo;
		}
		else {
			veloX += friction;
			if(veloX <= -maxVelo)
				veloX = -maxVelo;
		}
		if(veloY >= 0) {
			veloY -= friction;
			if(veloY >= maxVelo)
				veloY = maxVelo;
		}
		else {
			veloY += friction;
			if(veloY <= -maxVelo)
				veloY = -maxVelo;
		}
		setX(getX()+veloX);
		setY(getY()+veloY);
		
		sprite.rotate(veloY);
		
		outOfBounds();
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(sprite, getX() + sprite.getWidth(), getY() + sprite.getHeight()/2f, sprite.getWidth()/2, sprite.getHeight()/3, sprite.getWidth(), sprite.getHeight(), 1, 1, sprite.getRotation());
	}

	public int getHealth() {
		return health;
	}
}
