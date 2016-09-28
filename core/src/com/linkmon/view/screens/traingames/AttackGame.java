package com.linkmon.view.screens.traingames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.Timer;
import com.linkmon.view.screens.ScreenType;

public class AttackGame implements Screen {
	
	private Table container;
	
	private TrainingBag trainingBag;
	
	private Timer timer;
	
	private final float width = Gdx.graphics.getWidth();
	private final float height = Gdx.graphics.getHeight();
	
	private Skin skin;
	
	private Group group;
	
	private Image darken;
	
	private Label timeLeft;
	private String timeString = "Time Left: ";
	
	private Label hits;
	private String hitsString = "Hits: ";
	private String amountString = "/20";
	
	private EventManager eManager;
	
	public AttackGame(Group group, EventManager eManager) {
		skin = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin.addRegions(uiAtlas);
		timer = new Timer(30, false);
		this.group = group;
		
		this.eManager = eManager;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		container = new Table();
//		container.background(skin.getDrawable("darkenWorld"));
		container.setSize(width, height);
		
		darken = new Image(skin.getDrawable("darkenWorld"));
		darken.setSize(width, height);
		
		timer.start();
		
		trainingBag = new TrainingBag(width, height);
		
		BitmapFont font = new BitmapFont(Gdx.files.internal("fontSmall-export.fnt"),
		         Gdx.files.internal("fontSmall-export.png"), false);
		
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		timeLeft = new Label(timeString + " 20", labelStyle);
		timeLeft.setPosition(width - timeLeft.getWidth(), height - timeLeft.getHeight());
		
		hits = new Label(hitsString + "0" + amountString, labelStyle);
		hits.setPosition(0, height - timeLeft.getHeight());
		
		
		container.row();
		container.add(trainingBag);
		
		group.addActor(darken);
		group.addActor(container);
		group.addActor(timeLeft);
		group.addActor(hits);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		trainingBag.update();
		timeLeft.setText(timeString +  (30 - timer.getTotalElapsedTime()));
		hits.setText(hitsString + (20 - trainingBag.getHealth()) + amountString);
		
		if(30 - timer.getTotalElapsedTime() < 1) {
			eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
			eManager.notify(new MessageEvent(MessageEvents.GENERIC_MESSAGE, "FAILED", "You failed!", true));
		}
		if(trainingBag.getHealth() < 1) {
			eManager.notify(new ScreenEvent(ScreenEvents.TRAIN_LINKMON, StatType.ATTACK));
			eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.STAT_INCREASE_SCREEN));
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
		hits.remove();
		timeLeft.remove();
		darken.remove();
		container.remove();
		timer = null;
		trainingBag = null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
