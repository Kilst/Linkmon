package com.linkmon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.linkmon.componentmodel.linkmon.BirthDate;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.interfaces.ILinkmonStats;
import com.linkmon.view.screens.interfaces.IPlayerStats;
import com.linkmon.view.screens.interfaces.MyScreen;
import com.linkmon.view.screens.widgets.AnimationWidget;

public class CryoScreen implements Screen, MyScreen, ILinkmonStats {
	
	private Image backgroundImage;
	private Table container;
	
	private Table linkmonTable;
	private Table buttonsTable;
	
	private Button backButton;
	private Button savedLinkmonButton;
	private Button saveButton;
	
	private AnimationWidget linkmonAnimation;
	
	private Skin skin;
	
	private Group ui;
	
	private EventManager eManager;
	
	public CryoScreen(Group group, EventManager eManager) {
		
		this.eManager = eManager;
		
		this.ui = group;
		
		skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		
		container = new Table();
		linkmonTable = new Table();
		buttonsTable = new Table();
		
		backButton = new ImageButton(skin.getDrawable("backButton"));
		savedLinkmonButton = new ImageButton(skin.getDrawable("itemsButton"));
		saveButton = new ImageButton(skin.getDrawable("okayButton"));
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.GET_LINKMON_STATS, this));
		
		container.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		container.setBackground(skin.getDrawable("statsBackground"));
		linkmonTable.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.1f);
		linkmonTable.setBackground(skin.getDrawable("statsTable"));
		
		linkmonTable.add(linkmonAnimation);
		
		buttonsTable.add(backButton).align(Align.right);
		buttonsTable.row();
		buttonsTable.add(savedLinkmonButton).align(Align.right);
		buttonsTable.row();
		buttonsTable.add(saveButton).align(Align.right);
		buttonsTable.row();
		
		container.add(linkmonTable).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/1.1f);
		container.add(buttonsTable).expandX();
		
		ui.addActor(container);
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLinkmonStats(int id, int health, int attack, int defense, int speed, int careMistakes, BirthDate dob,
			int rank) {
		// TODO Auto-generated method stub
		linkmonAnimation = new AnimationWidget(id, 2f/76f);
	}

}
