package com.linkmon.view.screens.traingames;

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
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.game.GameClass;
import com.linkmon.model.linkmon.BirthDate;
import com.linkmon.view.UIRenderer;
import com.linkmon.view.screens.ScreenType;
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
	
	public StatIncreaseWindow(Group group, EventManager eManager) {
		this.eManager = eManager;
		uiGroup = group;
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
	}
	
	private void addListeners() {
		
		okayButton.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	            	eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
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
		
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_ADDED_STATS, this));

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
		
		container.add(statsTable).size(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/1.2f*UIRenderer.scaleY);
		container.row();
		container.add(okayButton).size(128*UIRenderer.scaleX, 64*UIRenderer.scaleY);
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
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob, int rank) {
		// TODO Auto-generated method stub
		attackString += attack;
		//if(linkmonController.getAddedAttack() > 0)
		defenseString += defense;
		speedString += speed;
		healthString += health;
	}

}