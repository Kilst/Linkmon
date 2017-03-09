package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.helpers.SmartFontGenerator;
import com.linkmon.model.linkmon.StatType;

public class StatWidget extends Table {
	
	private Label statLabel;
	private MyProgressBar pBar;
	
	private Skin skin;
	private Skin skin2;
	
	int stat;
	
	public StatWidget(Skin skin, Skin skin2, int stat, int statMax, int statType) {
		
		this.skin = skin;
		this.skin2 = skin2;
		this.stat = stat;
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceLoader.getSampleFont("large");
		
		findBackground(statType);
		
		this.pack();
		
		statLabel = new Label(""+stat, labelStyle);
		pBar = new MyProgressBar(skin2, stat, statMax);
		pBar.update(stat);
		
		this.add(statLabel).expand().align(Align.left).padLeft(this.getWidth()/1.9f).padTop(this.getHeight()/5);
		this.row();
		this.add(pBar).size(this.getWidth()/1.5f, 10).expand().align(Align.bottomLeft).padLeft(this.getWidth()/10).padBottom(this.getHeight()/4.5f);
	}

	private void findBackground(int statType) {
		switch(statType) {
			case(StatType.HEALTH) : {
				this.background(skin2.getDrawable("healthStatWidget"));
				break;
			}
			case(StatType.ATTACK) : {
				this.background(skin2.getDrawable("attackStatWidget"));
				break;
			}
			case(StatType.DEFENSE) : {
				this.background(skin2.getDrawable("defenseStatWidget"));
				break;
			}
			case(StatType.SPEED) : {
				this.background(skin2.getDrawable("speedStatWidget"));
				break;
			}
		}
	}
	
	public void setStat(int increaseStat) {
		statLabel.setText(""+(stat+increaseStat));
		pBar.update((int) (stat+increaseStat));
	}
	
	public MyProgressBar getProgressBar() {
		return pBar;
	}
}
