package com.linkmon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.screens.widgets.messages.ChatMessage;
import com.linkmon.view.screens.widgets.messages.ErrorMessage;
import com.linkmon.view.screens.widgets.messages.MessageBox;
import com.linkmon.view.screens.widgets.messages.RankUpMessage;

public class UIRenderer implements ScreenListener {
	
	public static final float scaleX = Gdx.graphics.getWidth()/1024;
	public static final float scaleY = Gdx.graphics.getHeight()/600;
	
	public static final float  scaleXY = scaleX * scaleY;
	
	public Stage stage;
	public Group ui;
	
	Image darken;
	
	private boolean lightOn = true;
	
	EventManager eManager;
	MessageManager messages;
	public MessageBox messageBox;
	
	public ChatMessage chatWindow;
	
	public UIRenderer(MessageManager messages, EventManager eManager) {
		this.messages = messages;
		this.eManager = eManager;
		
		this.eManager.addScreenListener(this);
		
		stage = new Stage();
		ui = new Group();
		stage.addActor(ui);
		
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
		stage.draw();
		
		// Used to darken the game world by time of day
		if(!lightOn) {
			if(!ui.getChildren().contains(darken, true)) {
				
				ui.addActor(darken);
				darken.toBack();
			}
			else
				darken.toBack();
		}
		else if(ui.getChildren().contains(darken, true)) {
			ui.removeActor(darken);
		}
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.LIGHT_SWAP): {
				lightOn = event.bool;
				break;
			}
		}
		return false;
	}
}
