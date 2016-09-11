package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.widgets.AnimationWidget;
import com.linkmon.view.screens.widgets.ReverseVignette;
import com.linkmon.view.screens.widgets.messages.MessageTable;

public class EvolutionScreen implements Screen {
	
	private Image background;
	private AnimationWidget animation;
	
	private Skin skin2;
	
	private Skin skin;
	
	private Group uiGroup;
	
	private MessageTable beginChat;
	private MessageTable endChat;
	
	private EventManager eManager;
	
	private ReverseVignette vign;
	
	private int oldId;
	private int newId;
	
	public EvolutionScreen(Group group, EventManager eManager, int oldId, int newId) {
		this.eManager = eManager;
		uiGroup = group;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.oldId = oldId;
		this.newId = newId;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		background = new Image(skin2.getDrawable("evolutionBackground"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		animation = new AnimationWidget(oldId, 2f/76f);
		animation.setPosition((Gdx.graphics.getWidth()/2)-(animation.getWidth()/2), Gdx.graphics.getHeight()/3);
		
		beginChat = new MessageTable(skin2);
		String[] messages = new String[2];
		messages[0] = "Your Linkmon is evolving!" + "  " + oldId;
		messages[1] = "I wonder what it will be?";
		beginChat.setText("EVOLUTION", messages);
		
		endChat = new MessageTable(skin2);
		String[] message = new String[1];
		message[0] = "Your Linkmon evolved into: " + newId + " <get name from id>";
		endChat.setText("EVOLUTION", message);
		
		endChat.setVisible(false);
		
		vign = new ReverseVignette(skin2);
		
		uiGroup.addActor(background);
		uiGroup.addActor(animation);
		uiGroup.addActor(vign);
//		uiGroup.addActor(endChat);
		uiGroup.addActor(beginChat);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(beginChat.isFinished()) {
			if(!vign.isFinished())
				vign.play();
			else if(!endChat.isVisible()) {
				endChat.setVisible(true);
				vign.remove();
				animation.remove();
				animation = new AnimationWidget(newId, 2f/76f);
				animation.setPosition((Gdx.graphics.getWidth()/2)-(animation.getWidth()/2), Gdx.graphics.getHeight()/3);
				uiGroup.addActor(animation);
				uiGroup.addActor(endChat);
			}
			
			if(endChat.isFinished())
				eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
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
		background.remove();
		animation.remove();
		vign.remove();
		endChat.remove();
		beginChat.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
