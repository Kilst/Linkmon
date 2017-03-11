package com.linkmon.view.screens.widgets.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.particles.ParticleIds;
import com.linkmon.view.screens.widgets.ScrollingLabel;

public class MessageTable extends Table {
	
	private boolean isFinished = false;
	
	private TiledDrawable bg;
	private NinePatch border;
	
	private Label heading;
	
	private ScrollingLabel label;
	
	private String message;
	
	private Skin skin;
	private Actor window = this;
	
	private EventManager eManager;
	
	public MessageTable(Skin skin2, EventManager eManager) {
		border = new NinePatch(skin2.getRegion("battleTextWindow"), 12, 12, 12, 12);
		bg = new TiledDrawable(skin2.getRegion("battleTextBackground"));
		
		setWidth(Gdx.graphics.getWidth());
		setHeight(Gdx.graphics.getHeight()/3f);
		
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		window = this;
		this.setTouchable(Touchable.enabled);
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(label.isFinished) {
            		if(!label.nextMessage()) {
            			window.remove();
            			isFinished = true;
            		}
            	}
            	else
            		label.instantText();
            }
		});
	}
	
	public MessageTable(Skin skin2, Actor chatMessage, EventManager eManager) {
		border = new NinePatch(skin2.getRegion("battleTextWindow"), 12, 12, 12, 12);
		bg = new TiledDrawable(skin2.getRegion("battleTextBackground"));
		
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		window = chatMessage;
		this.setTouchable(Touchable.enabled);
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(label.isFinished) {
            		if(!label.nextMessage()) {
            			window.remove();
            			isFinished = true;
            		}
            	}
            	else
            		label.instantText();
            }
		});
	}
	
	public void setText(String message) {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("medium");
		this.message = message;
		label = new ScrollingLabel(message, labelStyle, 0.09f);
		this.add(label).width(getWidth()-30).align(Align.topLeft).pad(15).expandY();
		label.setWrap(true);
	}
	
	public void setText(String heading, String[] messages) {
		
		LabelStyle labelStyle1 = new LabelStyle();
		labelStyle1.font = ResourceLoader.getLutFont("medium");
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getLutFont("large");
		
		this.heading = new Label(heading, labelStyle);
		this.add(this.heading).align(Align.left).padTop(15).padLeft(15);
		this.row();
		label = new ScrollingLabel(messages, labelStyle1, 0.05f);
		this.add(label).width(getWidth()-30).align(Align.topLeft).pad(15).expandY();
		label.setWrap(true);
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		//batch.draw(bg, getX(), getY(), getWidth(), getHeight());
		batch.setColor(1,1,1,0.95f);
		bg.draw(batch, getX() +11, getY()+11, getWidth()-22, getHeight()-22);
		border.draw(batch, getX(), getY(), getWidth(), getHeight());
		
		batch.setColor(1,1,1,1);
		
		super.draw(batch, alpha);
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

}
