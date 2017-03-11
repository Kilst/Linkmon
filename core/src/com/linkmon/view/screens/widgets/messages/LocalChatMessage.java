package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.particles.ParticleIds;

public class LocalChatMessage extends Actor {
	
	private Image characterImage;
	private MessageTable chatTable;
	private Skin skin;
	
	private Image darken;
	
	private Group gameUi;
	
	private EventManager eManager;
	
	private int messageType;
	
	private Timer timer;
	
	public LocalChatMessage(int id, int messageType, String[] messages, Group gameUi, EventManager eManager) {
		
		this.gameUi = gameUi;
		this.eManager = eManager;
		
		this.messageType = messageType;
		
		skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		
		characterImage = new Image(ResourceLoader.getRegionFromId(id)) {
			@Override
			public void act(float delta) {
				if(characterImage.getX()+characterImage.getWidth() > Gdx.graphics.getWidth())
					characterImage.setX(characterImage.getX()-1000f*delta);
			}
		};
		characterImage.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		darken = new Image(skin.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.getColor().a = 0.7f;
		
		chatTable = new MessageTable(skin, this, eManager);
		chatTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		
		chatTable.setText("Tinmon", messages);
		
		timer = new Timer(1, false);
		timer.start();
	}
	
	@Override
	public void act(float delta) {
		if(timer.checkTimer()) {
			eManager.notify(new ScreenEvent(ScreenEvents.ADD_PARTICLE_EFFECT, ParticleIds.CHAT_TAP, 1220, 50));
			timer.restart();
		}
	}

	public void show() {
		// TODO Auto-generated method stub
		gameUi.addActor(darken);
		darken.toFront();
		gameUi.addActor(characterImage);
		characterImage.toFront();
		gameUi.addActor(chatTable);
		chatTable.toFront();
		gameUi.addActor(this);
	}
	
	@Override
	public void toFront() {
		super.toFront();
		darken.toFront();
		characterImage.toFront();
		chatTable.toFront();
	}

	public void hide() {
		// TODO Auto-generated method stub
		darken.remove();
		characterImage.remove();
		chatTable.remove();
		this.remove();
	}
	
	@Override
	public boolean remove() {
		darken.remove();
		characterImage.remove();
		chatTable.remove();
		return super.remove();
	}
}
