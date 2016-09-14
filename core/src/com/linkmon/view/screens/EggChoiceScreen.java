package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.HelpMessages;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.linkmon.LinkmonIds;
import com.linkmon.view.screens.widgets.EggButton;
import com.linkmon.view.screens.widgets.ReverseVignette;

public class EggChoiceScreen implements Screen {
	
	private Label label;
	private Button egg1;
	private Button egg2;
	private Button egg3;
	private Button okayButton;
	private Table container;
	private Group uiGroup;

	private Skin skin;
	private Skin skin2;
	
	private int eggId = -1;
	
	private ReverseVignette vignette;
	
	private EventManager eManager;
	
	private GameClass game;
	
	private String playerName;
	
	private boolean startGame = false;
	
	public EggChoiceScreen(String playerName, Group uiGroup, GameClass game, EventManager eManager) {
		this.eManager = eManager;
		this.game = game;
		this.uiGroup = uiGroup;
		this.playerName = playerName;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		container = new Table();
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.background(skin2.getDrawable("container"));
		
		label = new Label("Choose an Egg:", skin);
		label.setFontScale(1.4f);
		
		egg1 = new EggButton(LinkmonIds.FIRE_BABY, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("spottedEgg.png")))), skin2.getDrawable("container"), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("selectedEggBackground.png")))));
		egg2 = new EggButton(LinkmonIds.FIRE_BABY, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("spottedEgg.png")))), skin2.getDrawable("container"), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("selectedEggBackground.png")))));
		egg3 = new EggButton(LinkmonIds.FIRE_BABY, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("spottedEgg.png")))), skin2.getDrawable("container"), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("selectedEggBackground.png")))));
		
		okayButton = new ImageButton(skin2.getDrawable("okayButton"));
		
		container.add(label).colspan(3).align(Align.top);
		container.row();
		container.add(egg1).expand();
		container.add(egg2).expand();
		container.add(egg3).expand();
		container.row();
		container.add(okayButton).colspan(3).align(Align.right);
		
		uiGroup.addActor(container);
		
		vignette = new ReverseVignette(skin2);
		vignette.tint(1, 0, 0);
		vignette.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		addListeners();
	}
	
	private void addListeners() {
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
        		//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
            	if(eggId != -1) {
            		uiGroup.addActor(vignette);
            		startGame = true;
            	}
            }
		});
		
	egg1.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
            		//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            	eggId = ((EggButton)egg1).eggId;
	            	((EggButton)egg1).selected = true;
	            	((EggButton)egg2).selected = false;
	            	((EggButton)egg3).selected = false;
//	            	game.startGame(playerName, LinkmonIds.FIRE_BABY);
//	            	Gdx.input.setOnscreenKeyboardVisible(false);
//	            	container.remove();
	            }
		});
	egg2.addListener(new ClickListener(){
        @Override 
        public void clicked(InputEvent event, float x, float y){
    		//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
        	eggId = ((EggButton)egg2).eggId;
        	((EggButton)egg1).selected = false;
        	((EggButton)egg2).selected = true;
        	((EggButton)egg3).selected = false;
	        }
		});
	egg3.addListener(new ClickListener(){
        @Override 
        public void clicked(InputEvent event, float x, float y){
    		//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
        	eggId = ((EggButton)egg3).eggId;
        	((EggButton)egg1).selected = false;
        	((EggButton)egg2).selected = false;
        	((EggButton)egg3).selected = true;
        	}
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(startGame) {
			vignette.play();
			if(vignette.isFinished()) {
//				game.startGame(playerName, eggId);
		    	Gdx.input.setOnscreenKeyboardVisible(false);
		    	container.remove();
			}
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
		vignette.remove();
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
