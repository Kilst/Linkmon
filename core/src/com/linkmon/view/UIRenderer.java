package com.linkmon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.messages.MessageListener;
import com.linkmon.game.GameClass;
import com.linkmon.messagesystem.MessageManager;
import com.linkmon.view.particles.ParticleLoader;
import com.linkmon.view.screens.interfaces.IOnlineScreen;
import com.linkmon.view.screens.widgets.messages.ChatMessage;
import com.linkmon.view.screens.widgets.messages.MessageBox;
import com.linkmon.view.screens.widgets.messages.MessageFactory;

public class UIRenderer implements MessageListener {
	
	public static final float scaleX = 1;
	public static final float scaleY = 1;
	
	public static final float  scaleXY = 1;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public Stage stage;
	public Group ui;
	
	EventManager eManager;
	MessageManager messages;
	public MessageBox messageBox;
	
	public ChatMessage chatWindow;
	
	private GameClass game;
	
	private MessageFactory messageFactory;
	
	private ParticleLoader pRenderer;
	
	public UIRenderer(MessageManager messages, GameClass game, EventManager eManager) {
		this.messages = messages;
		this.eManager = eManager;
		this.game = game;
		
		messageFactory = new MessageFactory(eManager);
		
		stage = new Stage(new FitViewport(1280, 720));
		stage.getViewport().apply();
		ui = new Group();
		stage.addActor(ui);
		
		eManager.addMessageListener(this);
	}
	
	private void displayMessageBox(MessageEvent event) {
		messageBox = messageFactory.getMessage(event);
		if(messageBox != null)
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
		
		// Render Particles
		stage.getBatch().begin();
		if(pRenderer != null)
			pRenderer.render(stage.getBatch());
		stage.getBatch().end();
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

	public ParticleLoader getpLoader() {
		return pRenderer;
	}

	public void addParticleLoader(ParticleLoader pLoader) {
		// TODO Auto-generated method stub
		pRenderer = pLoader;
	}
	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
}
