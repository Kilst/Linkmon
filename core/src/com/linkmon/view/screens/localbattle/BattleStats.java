package com.linkmon.view.screens.localbattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.view.screens.widgets.MyProgressBar;

public class BattleStats extends Table {
	
	private Label nameLabel;
	
	private Label healthLabel;
	private MyProgressBar pBar;
	
	private Label energyLabel;
	private MyProgressBar energyBar;
	
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
		
		energyLabel = new Label("Energy",skin);
		
		pBar = new MyProgressBar(skin2, 100, 100);
		
		energyBar = new MyProgressBar(skin2, 25, 50);
		energyBar.setColor(0, 0, 1, 1);
		
		this.background(skin2.getDrawable("tableNoHeading"));
		
		this.add(nameLabel).align(Align.left);
		this.row();
		this.add(healthLabel).align(Align.left);
		this.row();
		this.add(pBar).width(200).height(10).align(Align.left);
		this.row();
		this.add(energyLabel).align(Align.center);
		this.row();
		this.add(energyBar).width(200).height(10).align(Align.left);
	}
	
	public void setStats(String name, int health) {
		if(maxHealth == 0) {
			maxHealth = health;
			pBar.setTarget(maxHealth);
		}
		
		this.nameLabel.setText("Name: " + name);
		this.healthLabel.setText("Health: " + health + " / " + maxHealth);
		pBar.update(health);
		energyBar.update(25);
	}

	public void updateHealth(int health) {
		// TODO Auto-generated method stub
		pBar.update(health);
		this.healthLabel.setText("Health: " + health + " / " + maxHealth);
	}
	
	public void updateEnergy(int energy) {
		// TODO Auto-generated method stub
		energyBar.update(energy);
	}

}
