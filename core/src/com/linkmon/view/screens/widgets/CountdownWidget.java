package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;

public class CountdownWidget {
	
	private Label countdownLabel;
	private int countdown = 3;
	private float countdownTimer = 0;
	private boolean removeCountDown = false;
	
	private boolean gameStarted = false;
	
	EventManager eManager;
	
	public CountdownWidget(EventManager eManager, Group ui) {
		
		this.eManager = eManager;
		
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		countdownLabel = new Label(""+countdown, skin);
		countdownLabel.setFontScale(5f);
		countdownLabel.setPosition(1280/2, 720/2);
		
		countdownLabel.setColor(1, 0, 0, 1);
		
		ui.addActor(countdownLabel);
	}
	
	public boolean update(float delta) {
		
		
		if(!removeCountDown) {
				
			countdownTimer += delta/1000;
			countdownLabel.getColor().a -= 0.05f;
			
			if(countdownTimer > 1.5f) {
				Gdx.app.log("CountdownWidget", "TICK");
				countdownTimer = 0;
				
				countdown-= 1;
				if(countdown > 0) {
					countdownLabel.setText("" + countdown);
					countdownLabel.getColor().a = 1f;
					
					if(countdown == 2) {
						countdownLabel.getColor().r = 1;
						countdownLabel.getColor().g = 0.55f;
						countdownLabel.getColor().b = 0;
					}
					else {
						countdownLabel.getColor().r = 1;
						countdownLabel.getColor().g = 1f;
						countdownLabel.getColor().b = 0.8f;
					}
				}
				else {
					if(!gameStarted)
						countdownLabel.getColor().a = 1f;
					
					countdownLabel.getColor().r = 0;
					countdownLabel.getColor().g = 1f;
					countdownLabel.getColor().b = 0;
					
					countdownLabel.setText("GO!");
					gameStarted = true;
					eManager.notify(new ScreenEvent(ScreenEvents.START_MINIGAME));
					if(countdownLabel.getColor().a < 0.1f) {
						countdownLabel.remove();
						removeCountDown = true;
					}
				}
			}
		}
		
		return gameStarted;
	}

}
