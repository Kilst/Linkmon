package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.model.linkmon.MoveElementalType;
import com.linkmon.view.UIRenderer;

public class SelectableMoveButton extends Table implements ISelectable {

	private Label amount;
	private TextButton infoButton;
	private TextureRegion green;
	private boolean selected = false;

	private String moveName;
	private int moveId;
	private int type;
	private int slot;
	
	private SelectableMoveButton itemButton = this;
	private SelectionTable parent;
	
	public SelectableMoveButton(int moveId, String name, int type, int slot, int damage, int ignoreDamage, int energy, SelectionTable view) {
		// TODO Auto-generated constructor stub
		super();
		this.moveId = moveId;
		this.moveName = name;
		this.type = type;
		this.slot = slot;
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		
		SmartFontGenerator fontGen = new SmartFontGenerator();
		FileHandle exoFile = Gdx.files.internal("UI/lut.ttf");
		BitmapFont fontSmall = fontGen.createFont(exoFile, "move-small", 14);
		BitmapFont fontMedium = fontGen.createFont(exoFile, "move-medium", 18);
//		BitmapFont fontLarge = fontGen.createFont(exoFile, "move-large", 48);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		
		buttonStyle.checked = skin2.getDrawable("infoButton");
		buttonStyle.down = skin2.getDrawable("infoButton");
		buttonStyle.up = skin2.getDrawable("infoButton");
		buttonStyle.font = fontSmall;
		
		LabelStyle labelStyle = new LabelStyle();
		if(Gdx.graphics.getWidth() < 900)
			labelStyle.font = fontSmall;
		else
			labelStyle.font = fontMedium;
		
		infoButton = new TextButton("Info", buttonStyle);

		this.setTouchable(Touchable.enabled);
		this.setBackground(skin2.getDrawable("tableButton"));

		parent = view;
		
		Label itemNameLabel = new Label(name, labelStyle);
		
		Label moveType = new Label(getTypeString(), labelStyle);
		moveType.setAlignment(Align.center);
		Label moveDamage = new Label("Damage: "+damage+"\nIgnore Def: "+ignoreDamage+"\nEnergy Dmg: "+0, labelStyle);
		moveDamage.setAlignment(Align.left);
		Label moveIgnoreDamage = new Label(""+ignoreDamage, labelStyle);
		moveIgnoreDamage.setAlignment(Align.center);
		Label moveEnergy = new Label(""+energy, labelStyle);
		moveEnergy.setAlignment(Align.center);
		
		Image img = new Image(new NinePatch(skin2.getPatch("spacer")));
		img.getColor().a = 0.5f;
		
		Image img2 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img2.getColor().a = 0.5f;
		
		Image img3 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img3.getColor().a = 0.5f;
		
		Image img4 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img4.getColor().a = 0.5f;
		
		
		
		
		this.add(itemNameLabel).width(100*UIRenderer.scaleX).pad(5*UIRenderer.scaleXY).align(Align.left).expandX();
		itemNameLabel.setTouchable(Touchable.disabled);
		
		this.add(img).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img.setTouchable(Touchable.disabled);
		
		this.add(moveType).width(70*UIRenderer.scaleX).fill();
		
		this.add(img2).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img2.setTouchable(Touchable.disabled);
		
		this.add(moveDamage).width(110*UIRenderer.scaleX).fill();
		
//		this.add(img3).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
//		img3.setTouchable(Touchable.disabled);
//		
//		this.add(moveIgnoreDamage).width(50).fill();
		
		this.add(img4).width(3).height(60).align(Align.left).padLeft(15).padRight(15);
		img4.setTouchable(Touchable.disabled);
		
		this.add(moveEnergy).width(50*UIRenderer.scaleX).padRight(15).fill();
		
		green = new TextureRegion(skin2.getRegion("green"));
		
		
		this.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	parent.setSelectedItem(itemButton);
            }
		});
	}
	
	public SelectableMoveButton() {
		// TODO Auto-generated constructor stub
		super();
		
		Skin skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		Skin skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));

		SmartFontGenerator fontGen = new SmartFontGenerator();
		FileHandle exoFile = Gdx.files.internal("UI/samplefont.ttf");
		BitmapFont fontSmall = fontGen.createFont(exoFile, "exo-small", 14);
		BitmapFont fontMedium = fontGen.createFont(exoFile, "exo-medium", 18);
//		BitmapFont fontLarge = fontGen.createFont(exoFile, "exo-large", 48);
		
		LabelStyle labelStyle = new LabelStyle();
		if(Gdx.graphics.getWidth() < 900)
			labelStyle.font = fontSmall;
		else
			labelStyle.font = fontMedium;
		
		this.setBackground(skin2.getDrawable("tableButton"));
		this.setColor(0.3f, 0.3f, 1f, 0.75f);
		
		Label itemNameLabel = new Label("Name", labelStyle);
		itemNameLabel.setAlignment(Align.center);
		Label moveType = new Label("Type", labelStyle);
		moveType.setAlignment(Align.center);
		Label moveDamage = new Label("Damage", labelStyle);
		moveDamage.setAlignment(Align.center);
		Label moveEnergy = new Label("Energy", labelStyle);
		moveEnergy.setAlignment(Align.center);
		
		
		Image img = new Image(new NinePatch(skin2.getPatch("spacer")));
		img.getColor().a = 0.5f;
		
		Image img2 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img2.getColor().a = 0.5f;
		
		Image img3 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img3.getColor().a = 0.5f;
		
		Image img4 = new Image(new NinePatch(skin2.getPatch("spacer")));
		img4.getColor().a = 0.5f;
		
		
		
		
		this.add(itemNameLabel).width(100*UIRenderer.scaleX).pad(5*UIRenderer.scaleXY).align(Align.left).expandX();
		itemNameLabel.setTouchable(Touchable.disabled);
		
		this.add(img).width(3).height(30).align(Align.left).padLeft(15).padRight(15);
		img.setTouchable(Touchable.disabled);
		
		this.add(moveType).width(70*UIRenderer.scaleX).fill();
		
		this.add(img2).width(3).height(30).align(Align.left).padLeft(15).padRight(15);
		img2.setTouchable(Touchable.disabled);
		
		this.add(moveDamage).width(110*UIRenderer.scaleX).fill();
		
//		this.add(img3).width(3).height(30).align(Align.left).padLeft(15).padRight(15);
//		img3.setTouchable(Touchable.disabled);
		
//		this.add(moveIgnoreDamage).width(50).fill();
		
		this.add(img4).width(3).height(30).align(Align.left).padLeft(15).padRight(15);
		img4.setTouchable(Touchable.disabled);
		
		this.add(moveEnergy).width(50*UIRenderer.scaleX).padRight(15).fill();
	}
	
	private String getTypeString() {
		// TODO Auto-generated method stub
		String typeString = "";
		if(type == MoveElementalType.NOTYPE)
			typeString = "No Type";
		else if(type == MoveElementalType.FIRE)
			typeString = "Fire";
		else if(type == MoveElementalType.WATER)
			typeString = "Water";
		else if(type == MoveElementalType.ELECTRIC)
			typeString = "Electric";
		else if(type == MoveElementalType.EARTH)
			typeString = "Earth";
		return typeString;
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
	public int getType() {
		return type;
	}

	public int getSlot() {
		// TODO Auto-generated method stub
		return slot;
	}
}
