package com.linkmon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.messages.MessageListener;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.game.GameClass;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.screens.interfaces.IOnlineScreen;
import com.linkmon.view.screens.widgets.messages.ChatMessage;
import com.linkmon.view.screens.widgets.messages.MessageBox;
import com.linkmon.view.screens.widgets.messages.MessageFactory;
import com.linkmon.view.screens.widgets.messages.MessageType;
import com.linkmon.view.screens.widgets.messages.ReturnToMainMessageAction;

public class UIRenderer implements MessageListener {
	
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
	
	private MessageFactory messageFactory;
	
	public UIRenderer(MessageManager messages, GameClass game, EventManager eManager) {
		this.messages = messages;
		this.eManager = eManager;
		this.game = game;
		
		messageFactory = new MessageFactory(eManager);
		
		stage = new Stage();
		ui = new Group();
		stage.addActor(ui);
		
		eManager.addMessageListener(this);
	}
	
	private void displayMessageBox(MessageEvent event) {
		messageBox = messageFactory.getMessage(event);
		messageBox.addToUI(ui);
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
				Gdx.app.log("WorldRenderer","Displaying NETWORK message!");
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

	@Override
	public boolean onNotify(MessageEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(MessageEvents.CLEAR_CURRENT_MESSAGE): {
				messageBox = null;
				break;
			}
		}
		return false;
	}
}
