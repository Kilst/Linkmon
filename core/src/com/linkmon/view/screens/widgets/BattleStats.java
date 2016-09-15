package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;

public class BattleStats extends Table {
	
	private Label nameLabel;
	private Label healthLabel;
	private MyProgressBar pBar;
	
	private int maxHealth = 0;
	
	Skin skin;
	Skin skin2;
	
	public BattleStats() {
		
		this.skin = new Skin(Gdx.files.internal("Skins/uiskin.json"));
		
		skin2 = new Skin();
		TextureAtlas uiAtlas = ResourceLoader.assetManager.get(ResourceLoader.UIAtlas, TextureAtlas.class);
		skin2.addRegions(uiAtlas);
		
		this.nameLabel = new Label("",skin);
		this.healthLabel = new Label("",skin);
		
		pBar = new MyProgressBar(skin2, 100, 100);
		
		this.background(skin2.getDrawable("statsTable"));
		
		this.add(nameLabel).align(Align.left);
		this.row();
		this.add(healthLabel).align(Align.left);
		this.row();
		this.add(pBar).width(200).height(10).align(Align.left);
	}
	
	public void setStats(String name, int health) {
		if(maxHealth == 0)
			maxHealth = health;
		
		this.nameLabel.setText("Name: " + name);
		this.healthLabel.setText("Health: " + health + " / " + maxHealth);
		pBar.setTarget(maxHealth);
		pBar.update(health);
	}
	
	public void update(int health) {
		pBar.update(health);
		this.healthLabel.setText("Health: " + health + " / " + maxHealth);
	}

}
