package com.linkmon.messagesystem.messages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.WorldRenderer;

public class MessageBox extends Table {
	
	public Table messageBox;
	
	public Table headingTable;
	public Table messageTable;
	
	public Image heading;
	public Label message;
	
	public Button okayButton;
	
	public String messageString;
	
	public float scale = 0;
	
	public Image darken;
	
	public Skin skin;
	public Skin skin2;
	private EventManager eManager;
	
	WorldRenderer worldRenderer;
	
	public MessageBox(String messageString, WorldRenderer worldRenderer, EventManager eManager) {
		this.seteManager(eManager);
		this.messageString = messageString;
		this.setTransform(true);
		messageBox = this;
		
		
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.worldRenderer = worldRenderer;
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		this.setBackground(skin2.getDrawable("container"));
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setPosition(0, 0);
		this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.setSize((640/1.4f)*WorldRenderer.scaleXY, (480/1.4f)*WorldRenderer.scaleXY);
		
		
		this.setPosition((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		this.setOrigin((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.7f);
	}
	
	public void addToUi() {
		worldRenderer.ui.addActor(darken);
		worldRenderer.ui.addActor(this);
		darken.toFront();
		this.toFront();
	}
	
	public void setHeading(String headingFileString) {
		heading = new Image(skin2.getDrawable(headingFileString));
		this.add(heading).expandX().size(heading.getWidth()*WorldRenderer.scaleXY, heading.getHeight()*WorldRenderer.scaleXY);
		this.row();
	}
	
	public void setText(String messageString) {
		messageTable = new Table();
		messageTable.setBackground(skin2.getDrawable("table"));
		message = new Label(messageString , skin);
		message.setFontScale(1.2f, 1.2f);
		messageTable.add(message).fill();
		this.add(messageTable).expand().fill();
		this.row();
	}
	
	public void setOkay() {
		TextureRegionDrawable okay = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("okayButton.png"))));
		
		okayButton = new ImageButton(okay);
		this.add(okayButton).align(Align.right).size(okayButton.getWidth()*WorldRenderer.scaleXY, okayButton.getHeight()*WorldRenderer.scaleXY);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            	geteManager().notify(new MessageEvent(MessageEvents.CLEAR_CURRENT_MESSAGE));
            	worldRenderer.messageBox = null;
            }
		});
	}
	
	@Override
	public void act(float delta) {
		// Scale messageBox on show
		if(scale < 1)
			scale+=0.15;
		else
			scale = 1;
		
		this.setScale(scale);
	}

	public EventManager geteManager() {
		return eManager;
	}

	public void seteManager(EventManager eManager) {
		this.eManager = eManager;
	}
}
