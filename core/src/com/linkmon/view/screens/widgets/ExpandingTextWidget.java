package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ExpandingTextWidget extends Image {
	
	private static final double PERIOD = 128; // loop every 8 calls to updateNumber
	private static double SCALE = 0.2f; // go between 1 and 2

	private int pos = 0;
	private float sineNum = 0;
	
	private float scaleWidth;
	private float scaleHeight;
	
	private boolean finished = false;
	
	private int xPos = -1;
	private int yPos = -1;

	public ExpandingTextWidget(Drawable drawable) {
		super(drawable);
		// TODO Auto-generated constructor stub
		scaleWidth = this.getWidth();
		scaleHeight = this.getHeight();
	}
	
	public ExpandingTextWidget(Drawable drawable, int xPos, int yPos) {
		super(drawable);
		// TODO Auto-generated constructor stub
		scaleWidth = this.getWidth();
		scaleHeight = this.getHeight();
		
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void updateNumber() {
	    pos++;
	    sineNum = 1+(float)(Math.sin(pos*2*Math.PI/PERIOD)*(SCALE/2)+(SCALE/2));
	}
	
	@Override
	public void act(float delta) {
		updateNumber();
//		Gdx.app.log("ExpandingText", ""+sineNum);
		this.setSize(scaleWidth * sineNum, scaleHeight * sineNum);
		if(xPos != -1)
			this.setPosition(xPos-this.getWidth()/2, yPos-this.getHeight()/2);
		else
			this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
	}

}
