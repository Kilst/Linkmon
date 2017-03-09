package com.linkmon.view.screens.localbattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.controller.LocalBattleController;
import com.linkmon.controller.PlayerController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;

public class BattleIntroWidget extends Actor {
	
	private Group uiGroup;
	
	private Skin skin2;
	
	private Image vsImage;
	private Image background;
	
	private Image player;
	private Image opponent;
	
	private Timer timer1;
	private Timer timer2;
	private Timer timer3;
	private Timer timer4;
	
	public BattleIntroWidget(Group group, int playerLinkmonId, int opponentLinkmonId) {
		uiGroup = group;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		// Build Elements
		
		background = new Image(ResourceLoader.assetManager.get("battleIntroBackground.png", Texture.class));
		background.setTouchable(Touchable.enabled);
		vsImage = new Image(skin2.getDrawable("vsImage"));
		vsImage.setPosition(1280/2-vsImage.getWidth()/2, 720/2-vsImage.getHeight()/2);
		vsImage.setScale(10);
		vsImage.setVisible(false);
		
		TextureRegion playerTex = new TextureRegion(ResourceLoader.getLinkmonAnimFromId(playerLinkmonId)[0].first());
		playerTex.flip(true, false);
		
		player = new Image(playerTex);
		player.setPosition(0-player.getWidth(), 720/2-player.getHeight()/2);
		opponent = new Image(ResourceLoader.getLinkmonAnimFromId(opponentLinkmonId)[0].first());
		opponent.setPosition(1280+opponent.getWidth(), 720/2-opponent.getHeight()/2);
		
		// Set timers for the act(delta) method
		
		timer1 = new Timer(1, false);
		timer2 = new Timer(3, false);
		timer3 = new Timer(4, false);
		timer4 = new Timer(8, false);
		
		timer1.start();
		timer2.start();
		timer3.start();
		timer4.start();
		
		uiGroup.addActor(background);
		uiGroup.addActor(vsImage);
		uiGroup.addActor(player);
		uiGroup.addActor(opponent);
	}

	@Override
	public void act(float delta) {
		
		// Bring the widget toFront
		background.toFront();
		player.toFront();
		opponent.toFront();
		vsImage.toFront();		
		if(timer1.checkTimer()) {
			// Slide player image over
			if(player.getX() < 0)
				player.setX(player.getX()+15);
				
		}
		if(timer2.checkTimer()) {
			// Scale down vsImage
			vsImage.setVisible(true);
			if(vsImage.getScaleX() > 1)
				vsImage.setScale(vsImage.getScaleX()-0.5f, vsImage.getScaleY()-0.5f);
		}
		if(timer3.checkTimer()) {
			// Slide opponent image over
			if(opponent.getX()+opponent.getWidth() > 1280)
				opponent.setX(opponent.getX()-15);
		}
		if(timer4.checkTimer()) {
			// Ended
			background.remove();
			vsImage.remove();
			player.remove();
			opponent.remove();
			this.remove();
		}
	}

}
