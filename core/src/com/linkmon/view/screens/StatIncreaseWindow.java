package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.linkmon.controller.LinkmonController;
import com.linkmon.controller.ScreenController;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.game.GameClass;
import com.linkmon.model.gameobject.linkmon.BirthDate;
import com.linkmon.view.WorldRenderer;
import com.linkmon.view.screens.interfaces.ILinkmonAddedStats;

public class StatIncreaseWindow implements Screen, ILinkmonAddedStats {
	
	private Table container;
	private Table statsTable;
	private Group uiGroup;
	private Button okayButton;
	private GameClass game;
	private Label attackLabel;
	public String attackString;
	private Label defenseLabel;
	public String defenseString;
	private Label speedLabel;
	public String speedString;
	private Label healthLabel;
	public String healthString;
	
	
	private EventManager eManager;
	private Skin skin;
	
	private ScreenController screenController;
	private LinkmonController linkmonController;
	
	public StatIncreaseWindow(Group group, ScreenController screenController, LinkmonController linkmonController, EventManager eManager) {
		this.eManager = eManager;
		this.screenController = screenController;
		this.linkmonController = linkmonController;
		uiGroup = group;
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
	}
	
	public void update() {
		screenController.updateWindow(this);
	}
	
	private void addListeners() {
		
		okayButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	linkmonController.updateLinkmonStats();
	            	eManager.notify(new ControllerEvent(ControllerEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	            }
			});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		container = new Table(skin);
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("default-rect"));
		
		statsTable = new Table();
		statsTable.setBackground(skin.getDrawable("default-rect"));
		
		
		
		healthString = "Health: ";
		attackString = "Attack: ";
		defenseString = "Defense: ";
		speedString = "Speed: ";
		
		update();

		healthLabel = new Label(healthString, skin);
		attackLabel = new Label(attackString, skin);
		defenseLabel = new Label(defenseString, skin);
		speedLabel = new Label(speedString, skin);
		
		
		statsTable.add(healthLabel);
		statsTable.row();
		statsTable.add(attackLabel);
		statsTable.row();
		statsTable.add(defenseLabel);
		statsTable.row();
		statsTable.add(speedLabel);
		
		
		
		
		TextureRegionDrawable okay = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("okayButton.png"))));
		okayButton = new ImageButton(okay);
		
		container.add(statsTable).size(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/1.2f*WorldRenderer.scaleY);
		container.row();
		container.add(okayButton).size(128*WorldRenderer.scaleX, 64*WorldRenderer.scaleY);
		addListeners();
		
		
		uiGroup.addActor(container);
		uiGroup.toFront();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
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
		container.remove();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAddedStats(int addedHealth, int addedAttack, int addedDefense, int addedSpeed) {
		// TODO Auto-generated method stub
		attackString += " + " + addedAttack;
		//if(linkmonController.getAddedAttack() > 0)
		defenseString += " + " + addedDefense;
		speedString += " + " + addedSpeed;
		healthString += " + " + addedHealth;
	}

	@Override
	public void getLinkmonStats(int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		// TODO Auto-generated method stub
		attackString += attack;
		//if(linkmonController.getAddedAttack() > 0)
		defenseString += defense;
		speedString += speed;
		healthString += health;
	}

}