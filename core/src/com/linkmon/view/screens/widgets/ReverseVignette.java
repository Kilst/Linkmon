package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ReverseVignette extends Actor {
	
	private Image vignette;
	private Image lighten;
	
	private boolean finishedLoading = false;
	
	public ReverseVignette(Skin skin2) {
		vignette = new Image(skin2.getDrawable("whiteReverseVignette")) {
			@Override
			public void act(float delta) {
				if (vignette.getColor().a <= 1f)
					vignette.getColor().a += 0.003;
			}
		};
		vignette.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		vignette.getColor().a = 0.3f;
		lighten = new Image(skin2.getDrawable("lighten")) {
			@Override
			public void act(float delta) {
				if (lighten.getColor().a <= 1f)
					lighten.getColor().a += 0.003;
				else {
					finishedLoading = true;
//					lighten.remove();
//					vignette.remove();
				}
			}
		};
		lighten.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lighten.getColor().a = 0;
	}
	
	public void tint(float r, float g, float b) {
		lighten.setColor(r, g, b, lighten.getColor().a);
		vignette.setColor(r, g, b, vignette.getColor().a);
	}
	
	public boolean isFinished() {
		return finishedLoading;
	}
	
	public void play() {
		vignette.act(Gdx.graphics.getDeltaTime());
		lighten.act(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		lighten.draw(batch, alpha);
		vignette.draw(batch, alpha);
	}

}
