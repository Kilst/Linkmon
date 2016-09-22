package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.UIRenderer;

public class SelectableMoveButton extends Table implements ISelectable {

	private Label amount;
	private TextButton infoButton;
	private TextureRegion green;
	private boolean selected = false;

	private String moveName;
	private int moveId;
	private int power;
	private int ignoreDefence;
	private int energy;
	
	private SelectableMoveButton itemButton = this;
	private SelectionTable parent;
	
	public SelectableMoveButton(int id, String name, SelectionTable view) {
		// TODO Auto-generated constructor stub
		super();
		this.moveId = id;
		this.moveName = name;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("infoButton");
		buttonStyle.down = skin2.getDrawable("infoButton");
		buttonStyle.up = skin2.getDrawable("infoButton");
		buttonStyle.font = skin.getFont("default-font");
		
		infoButton = new TextButton("Info", buttonStyle);

		this.setTouchable(Touchable.enabled);
		this.setBackground(skin2.getDrawable("tableButton"));

		parent = view;
		
		Label itemNameLabel = new Label(name, skin);
		
		Image img = new Image(new NinePatch(skin2.getPatch("spacer")));
		img.getColor().a = 0.5f;
		
		Image img2 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img2.getColor().a = 0.5f;
		
		Image img3 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img3.getColor().a = 0.5f;
		
		
		
		
		this.add(itemNameLabel).pad(5*UIRenderer.scaleXY).align(Align.left).expandX();
		itemNameLabel.setTouchable(Touchable.disabled);
		
		this.add(img).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img.setTouchable(Touchable.disabled);
		
		this.add(infoButton).width(100).padLeft(2*UIRenderer.scaleXY).padRight(2*UIRenderer.scaleXY).fill();
		
		this.add(img2).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img2.setTouchable(Touchable.disabled);
		
		
		this.add(img3).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img3.setTouchable(Touchable.disabled);
		
		green = new TextureRegion(skin2.getRegion("green"));
		
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	parent.setSelectedItem(itemButton);
            }
		});
	}
	
	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		this.selected = selected;
	}
	
	public int getMoveId() {
		return moveId;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		batch.setColor(1, 1, 1, 0.7f);
		if(selected)
			batch.draw(green, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		batch.setColor(1, 1, 1, 1);
		super.draw(batch, alpha);
//		amount.draw(batch, alpha);
	}
}
