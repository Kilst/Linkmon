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
import com.linkmon.game.GameClass;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.screens.interfaces.IOnlineScreen;
import com.linkmon.view.screens.widgets.messages.ChatMessage;
import com.linkmon.view.screens.widgets.messages.ErrorMessage;
import com.linkmon.view.screens.widgets.messages.MessageBox;
import com.linkmon.view.screens.widgets.messages.RankUpMessage;

public class UIRenderer {
	
	public static final float scaleX = Gdx.graphics.getWidth()/1024;
	public static final float scaleY = Gdx.graphics.getHeight()/600;
	
	public static final float  scaleXY = scaleX * scaleY;
	
	public Stage stage;
	public Group ui;
	
	EventManager eManager;
	MessageManager messages;
	public MessageBox messageBox;
	
	public ChatMessage chatWindow;
	
	private GameClass game;
	
	public UIRenderer(MessageManager messages, GameClass game, EventManager eManager) {
		this.messages = messages;
		this.eManager = eManager;
		this.game = game;
		
		stage = new Stage();
		ui = new Group();
		stage.addActor(ui);
	}
	
	private void displayMessageBox(MessageEvent event) {
		switch(event.eventId) {
			case(MessageEvents.RANK_UP) : {
				messageBox = new RankUpMessage(event.messageType, event.message, event.rank, this, eManager);
				break;
			}
			case(MessageEvents.DISCONNECTED_SERVER) : {
				messageBox = new ErrorMessage(event.messageType, event.message, this, eManager);
				break;
			}
			case(MessageEvents.POOP_MISTAKE) : {
				messageBox = new ErrorMessage(event.messageType, event.message, this, eManager);
				break;
			}
			case(MessageEvents.EXAHUSTED_TRAIN_MESSAGE) : {
				messageBox = new ErrorMessage(event.messageType, event.message, this, eManager);
				break;
			}
		}
	}
	
	private void displayChatWindow(MessageEvent event) {
		switch(event.eventId) {
			case(MessageEvents.SHOW_CHAT) : {
				chatWindow = new ChatMessage(event.value, event.messageType, event.messages, this, ui, eManager);
				chatWindow.show();
				break;
			}
		}	
	}
	
	public void render() {
		if((messageBox == null && chatWindow == null)) {
			if(game.getScreen() instanceof IOnlineScreen && messages.getCurrentNetworkMessage() != null) {
				displayMessageBox(messages.getCurrentNetworkMessage());
			}
			else if (messages.getCurrentMessage() != null) {
				Gdx.app.log("WorldRenderer","Displaying message!");
				displayMessageBox(messages.getCurrentMessage());
				displayChatWindow(messages.getCurrentMessage());
			}
		}
		
		stage.act();
		if(chatWindow != null)
			chatWindow.toFront();
		stage.draw();
	}
}
