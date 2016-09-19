package com.linkmon.view.screens.traingames;

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

	public ExpandingTextWidget(Drawable drawable) {
		super(drawable);
		// TODO Auto-generated constructor stub
		scaleWidth = this.getWidth();
		scaleHeight = this.getHeight();
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
		this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
	}

}
