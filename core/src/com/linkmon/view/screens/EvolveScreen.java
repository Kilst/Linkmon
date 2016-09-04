package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.widgets.ReverseVignette;

public class EvolveScreen implements Screen {
	
	private Group ui;
	private EventManager eManager;
	private ReverseVignette vignette;
	private Image darken;
	
	private Skin skin;
	
	public EvolveScreen(Group ui, EventManager eManager) {
		skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		this.ui = ui;
		this.eManager = eManager;
		vignette = new ReverseVignette(skin);
		
		darken = new Image(skin.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		darken.getColor().a = 0.9f;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		ui.addActor(darken);
		ui.addActor(vignette);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		darken.toFront();
		vignette.toFront();
		vignette.act(delta);
		if(vignette.isFinished())
			eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
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
		darken.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
