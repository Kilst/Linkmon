package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.WorldRenderer;

public class TrainWindow implements Screen {
	
	private Table container;
	private Group uiGroup;
	private Button attack;
	private Button defense;
	private Button health;
	private Button speed;
	private Button backButton;
	private Table table;
	private EventManager eManager;
	
	
	public TrainWindow(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		uiGroup = group;
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		Image title = new Image(new TextureRegion(new Texture(Gdx.files.internal("trainButton.png"))));

		TextureRegionDrawable image = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("attackButton.png"))));
		//statsLabel = new Label("Stats", skin);
		TextureRegionDrawable back = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("backButton.png"))));
		backButton = new ImageButton(back);
		
		table = new Table(skin);
		TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("menuBackground.png"))));
		table.setBackground(bg);
		
		attack = new ImageButton(image);
		image = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("defenseButton.png"))));
		defense = new ImageButton(image);
		image = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("speedButton.png"))));
		speed = new ImageButton(image);
		image = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("healthButton.png"))));
		health = new ImageButton(image);
		
		table.add(attack).size(Gdx.graphics.getWidth()/5, image.getRegion().getRegionHeight());
		table.row();
		table.add(defense).size(Gdx.graphics.getWidth()/5, image.getRegion().getRegionHeight());
		table.row();
		table.add(speed).size(Gdx.graphics.getWidth()/5, image.getRegion().getRegionHeight());
		table.row();
		table.add(health).size(Gdx.graphics.getWidth()/5, image.getRegion().getRegionHeight());
		container.add(title);
		container.row();
		container.add(table).size(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/1.4f*WorldRenderer.scaleY);
		container.row();
		container.add(backButton).align(Align.right).size(128*WorldRenderer.scaleX, 64*WorldRenderer.scaleY);
		addListeners();
	}
	
	private void addListeners() {
		
		attack.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.TRAIN_ATTACK_SCREEN));
	            }
			});
		defense.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.TRAIN_DEFENSE_SCREEN));
            }
		});
		speed.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.TRAIN_SPEED_SCREEN));
            }
		});
		health.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//eManager.notify(new ViewEvent(ViewEvents.SWAP_SCREEN, ScreenType.TRAIN_HEALTH_SCREEN));
            }
		});
		backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
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
