package com.linkmon.view.screens.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.interfaces.IOnlineScreen;

public class BattleIntroScreen implements Screen, IOnlineScreen {
	
	
	private Table container;
	private Table oppTable;
	private Table playerTable;
	private Image scrollImage;
	private float imageSpeed = 100f;
	
	private Skin skin;
	
	private Group ui;
	
	public BattleIntroScreen(Group ui) {
		this.ui = ui;
		skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		scrollImage = new Image(skin.getDrawable("menuBackground"));
		scrollImage.setPosition(1, 0);
	}
	
	public void exponentialSpeedEquation(float x) {
//		y = Math.pow(0.25f, x);
		imageSpeed = 150*((float) Math.pow(0.993, x));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		ui.addActor(scrollImage);
		ui.addActor(container);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(scrollImage.getX()+scrollImage.getWidth() < Gdx.graphics.getWidth()) {
			exponentialSpeedEquation(scrollImage.getX());
			scrollImage.setPosition(scrollImage.getX() + imageSpeed, scrollImage.getY());
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		scrollImage.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMessage() {
		// TODO Auto-generated method stub
		
	}

}
