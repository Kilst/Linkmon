package com.linkmon.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.linkmon.controller.LinkmonController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.eventmanager.model.ModelListener;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.eventmanager.view.ViewListener;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.messagesystem.messages.ChatMessage;
import com.linkmon.messagesystem.messages.ErrorMessage;
import com.linkmon.messagesystem.messages.MessageBox;
import com.linkmon.messagesystem.messages.RankUpMessage;
import com.linkmon.model.gameobject.Direction;
import com.linkmon.model.gameobject.poop.Poop;
import com.linkmon.networking.PacketType;
import com.linkmon.view.screens.ScreenType;

public class WorldRenderer implements ViewListener {
	
	public static final float scaleX = Gdx.graphics.getWidth()/1024;
	public static final float scaleY = Gdx.graphics.getHeight()/600;
	
	public static final float  scaleXY = scaleX * scaleY;
	
	public Stage stage;
	
	public Group world;
	public Group ui;
	
	Image darken;
	
	private boolean lightOn = true;
	
	EventManager eManager;
	MessageManager messages;
	public MessageBox messageBox;
	
	public ChatMessage chatWindow;
	
	public WorldRenderer(MessageManager messages, EventManager eManager) {
		this.messages = messages;
		this.eManager = eManager;
		
		this.eManager.addViewListener(this);
		
		stage = new Stage();
		world = new Group();
		ui = new Group();
		stage.addActor(ui);
		stage.addActor(world);
		
		// Used to darken the game world
		darken = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("darkenWorld.png")))));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.7f);
		darken.setTouchable(Touchable.disabled);
//		game.addActor(darken);
	}
	
	private void displayMessageBox(MessageEvent event) {
		switch(event.eventId) {
			case(MessageEvents.RANK_UP) : {
				messageBox = new RankUpMessage(event.message, event.rank, this, eManager);
				break;
			}
			case(MessageEvents.DISCONNECTED_SERVER) : {
				messageBox = new ErrorMessage(event.message, this, eManager, event.returnToMain);
				break;
			}
			case(MessageEvents.POOP_MISTAKE) : {
				messageBox = new ErrorMessage(event.message, this, eManager, event.returnToMain);
				break;
			}
			case(MessageEvents.EXAHUSTED_TRAIN_MESSAGE) : {
				messageBox = new ErrorMessage(event.message, this, eManager, event.returnToMain);
				break;
			}
		}
	}
	
	private void displayChatWindow(MessageEvent event) {
		switch(event.eventId) {
			case(MessageEvents.SHOW_CHAT) : {
				chatWindow = new ChatMessage(event.value, event.messages, this, ui, eManager);
				chatWindow.show();
				break;
			}
		}	
	}
	
	public void render() {
		if((messageBox == null && chatWindow == null) && messages.getCurrentMessage() != null) {
			Gdx.app.log("WorldRenderer","Displaying message!");
			displayMessageBox(messages.getCurrentMessage());
			displayChatWindow(messages.getCurrentMessage());
		}
		
		stage.act();
		if(chatWindow != null)
			chatWindow.toFront();
		ui.toFront();
		stage.draw();
		
		// Used to darken the game world by time of day
		if(!lightOn) {
			if(!world.getChildren().contains(darken, true)) {
				
				world.addActor(darken);
				darken.toFront();
			}
			else
				darken.toFront();
		}
		else if(world.getChildren().contains(darken, true)) {
			world.removeActor(darken);
		}
	}

	@Override
	public void onNotify(ViewEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ViewEvents.UPDATE_LIGHT): {
				lightOn = event.status;
				break;
			}
		}
	}
}
