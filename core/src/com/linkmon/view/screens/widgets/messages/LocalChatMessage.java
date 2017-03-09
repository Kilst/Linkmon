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
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;

public class LocalChatMessage extends Actor {
	
	private Image characterImage;
	private MessageTable chatTable;
	private Skin skin;
	
	private Image darken;
	
	private Group gameUi;
	
	private EventManager eManager;
	
	private int messageType;
	
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
		
		chatTable = new MessageTable(skin, this);
		chatTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
		
		chatTable.setText("Tinmon", messages);
		
	}

	public void show() {
		// TODO Auto-generated method stub
		gameUi.addActor(darken);
		darken.toFront();
		gameUi.addActor(characterImage);
		characterImage.toFront();
		gameUi.addActor(chatTable);
		chatTable.toFront();
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
	}
	
	@Override
	public boolean remove() {
		darken.remove();
		characterImage.remove();
		chatTable.remove();
		return super.remove();
	}
}
