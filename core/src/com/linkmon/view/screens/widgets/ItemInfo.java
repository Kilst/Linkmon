package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;

public class ItemInfo extends Table {
	
	public Table messageBox;
	
	public Table headingTable;
	public Table messageTable;
	
	public Table heading;
	public Label message;
	
	public Button okayButton;
	
	public String messageString;
	
	public float scale = 0;
	
	public Image darken;
	
	public Skin skin;
	public Skin skin2;
	
	private Group gameUi;
	
	public ItemInfo(String text, Group ui) {
		this.gameUi = ui;
		messageString = text;
		this.setTransform(true);
		messageBox = this;
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("button");
		buttonStyle.down = skin2.getDrawable("button");
		buttonStyle.up = skin2.getDrawable("button");
		buttonStyle.font = skin.getFont("default-font");
		
		this.setBackground(skin2.getDrawable("tableNoHeading"));
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setPosition(0, 0);
		this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.setSize((640/1.4f)*UIRenderer.scaleXY, (480/1.4f)*UIRenderer.scaleXY);
		
		
		this.setPosition((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		this.setOrigin((Gdx.graphics.getWidth()/2f) - (this.getWidth()/2), (Gdx.graphics.getHeight()/2f) - (this.getHeight()/2));
		
		
		darken = new Image(skin2.getDrawable("darkenWorld"));
		darken.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		darken.setColor(1f, 1f, 1f, 0.85f);
		
		heading = new Table();
		heading.setBackground(skin2.getDrawable("title"));
		heading.setSize(290, 136);
		Label title = new Label("Item Info", skin);
		title.setFontScale(1.2f);
		heading.add(title).padBottom(15);
		
		messageTable = new Table();
		messageTable.setBackground(skin2.getDrawable("tableNoHeading"));
		message = new Label(messageString , skin);
		message.setFontScale(1.2f);
		messageTable.add(message).fill();
		
		
		this.add(heading).padTop(-50);
		this.row();
		this.add(messageTable).expand().fill();
		this.row();
		
		okayButton = new TextButton("Okay", buttonStyle);
		this.add(okayButton).align(Align.right).size(okayButton.getWidth()*UIRenderer.scaleXY, okayButton.getHeight()*UIRenderer.scaleXY);
		this.row();
		
		okayButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	messageBox.remove();
            	darken.remove();
            }
		});
		
		gameUi.addActor(darken);
		gameUi.addActor(this);
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

}
