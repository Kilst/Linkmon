package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.game.GameClass;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.ScrollingLabel;

public class IntroScreen implements Screen {
	
	private Table container;
	private Group uiGroup;
	private Skin skin;
	private Skin skin2;
	
	private EventManager eManager;
	private boolean finishedLoading = false;
	
	private Image introImage;
	private Image titleImage;
	private ScrollingLabel introLabel;
	
	private AnimationWidget loading;
	
	private GameClass game;
	
	public IntroScreen(GameClass game, Group group, EventManager eManager) {
		this.game = game;
		uiGroup = group;
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		this.eManager = eManager;
		
		loading = new AnimationWidget(Gdx.files.internal("Animations/Loading/loading.pack"), 0.5f);
		loading.setPosition(Gdx.graphics.getWidth()-loading.getWidth(), 0);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin2.getDrawable("darkenWorld"));
		
		introImage = new Image(ResourceLoader.getRegionFromId(1)){
					@Override
					public void act(float delta) {
						if (introImage.getColor().a < 1f)
							introImage.getColor().a += 0.001;
					}
		};
		introImage.getColor().a = 0;
		
		container.add(introImage);
		container.row();
		
		titleImage = new Image(skin2.getDrawable("mainTitle")){
			@Override
			public void act(float delta) {
				if (titleImage.getColor().a < 1f)
					titleImage.getColor().a += 0.001;
				else if(introLabel == null) {
						introLabel = new ScrollingLabel("By Kilst", skin, 0.1f){
						@Override
						public void act(float delta) {
							if(introLabel.isFinished)
								if (introLabel.getColor().a > 0f)
									introLabel.getColor().a -= 0.01;
								else
									finishedLoading = true;
						}
					};
					container.add(introLabel).padTop(20*WorldRenderer.scaleXY);
				}
			}
		};
		titleImage.getColor().a = 0;
		
		container.add(titleImage);
		container.row();
		uiGroup.addActor(container);
		uiGroup.addActor(loading);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		introImage.act(delta);
		titleImage.act(delta);
		introImage.act(delta);
		if(finishedLoading) {
//			if(player.getName() == "")
//				eManager.notify(new ViewEvent(ViewEvents.SWAP_SCREEN, ScreenType.NEW_GAME_SCREEN));
//			else
			game.setScreen(new NewGameScreen(game, uiGroup, eManager));
				//eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.NEW_GAME_SCREEN));
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
