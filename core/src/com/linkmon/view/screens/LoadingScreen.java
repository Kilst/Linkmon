package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.linkmon.controller.SoundController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.newgame.NewGameScreen;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.ScrollingLabel;

public class LoadingScreen implements Screen {
	
	private Table container;
	private Group uiGroup;
	private Skin skin;
	//private Skin skin2;
	
	private EventManager eManager;
	private boolean finishedLoading = false;
	
	private Image introImage;
	private Image titleImage;
	private ScrollingLabel introLabel;
	
	private AnimationWidget loading;
	
	private GameClass game;
	SoundController sound;
	
	public LoadingScreen(GameClass game, Group group, EventManager eManager, SoundController sound) {
		this.game = game;
		uiGroup = group;
		this.sound = sound;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
//		skin2 = new Skin();
//		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
//		skin2.addRegions(uiAtlas);
		this.eManager = eManager;
		
		loading = new AnimationWidget(Gdx.files.internal("Animations/Loading/loading.pack"), 0.5f);
		loading.setPosition(Gdx.graphics.getWidth()-loading.getWidth(), 0);
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(new Image(new Texture(Gdx.files.internal("darkenWorld.png"))).getDrawable());
		
		introImage = new Image(new Texture(Gdx.files.internal("my-robot.png"))){
					@Override
					public void act(float delta) {
						if (introImage.getColor().a < 1f)
							introImage.getColor().a += 0.03;
						else if (introImage.getColor().a > 1f)
							introImage.getColor().a = 1;
					}
		};
		introImage.getColor().a = 0;
		
		container.add(introImage);
		container.row();
		
		titleImage = new Image(new Texture(Gdx.files.internal("mainTitle.png"))){
			@Override
			public void act(float delta) {
				if (titleImage.getColor().a < 1f)
					titleImage.getColor().a += 0.03;
				else if (titleImage.getColor().a > 1f)
					titleImage.getColor().a = 1;
				else if(!introLabel.isVisible()) {
					introLabel.setVisible(true);			
				}
			}
		};
		titleImage.getColor().a = 0;
		
		container.add(titleImage);
		container.row();
		
		introLabel = new ScrollingLabel("By Kilst", skin, 0.05f){
			@Override
			public void act(float delta) {
				if(introLabel.isFinished)
					finishedLoading = true;
			}
		};
		introLabel.setVisible(false);
		container.add(introLabel).padTop(20*UIRenderer.scaleXY);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		
		uiGroup.addActor(container);
		uiGroup.addActor(loading);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.app.log("LOADING", "Rendering");
		if (!ResourceLoader.assetManager.update()){
			sound.update();
        }
		else if(finishedLoading) {
			game.startGame();
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
		container.setVisible(false);
		container.remove();
		loading.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
