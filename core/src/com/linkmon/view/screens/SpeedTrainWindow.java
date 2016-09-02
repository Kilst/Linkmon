package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.WorldRenderer;

public class SpeedTrainWindow implements Screen {
	
	private Table container;
	private Group uiGroup;
	private Button trainingBag;
	
	private EventManager eManager;
	
	private int trainingBagHealth = 20;
	
	public SpeedTrainWindow(Group group, EventManager eManager) {
		this.eManager = eManager;
		uiGroup = group;
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		TextureRegion region = ResourceLoader.getRegionFromId(0);
		
		trainingBag = new ImageButton(new TextureRegionDrawable(region));
		container.add(trainingBag).size(64*WorldRenderer.scaleX, 64*WorldRenderer.scaleY);
		addListeners();
	}
	
	private void addListeners() {
		
		trainingBag.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	if(trainingBagHealth > 0)
	            		trainingBagHealth -= 1;
	            	else
	            		eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            }
			});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
