package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.linkmon.model.linkmon.StatType;

public class StatWidget extends Table {
	
	private Label statLabel;
	private MyProgressBar pBar;
	
	private Skin skin;
	private Skin skin2;
	
	public StatWidget(Skin skin, Skin skin2, int stat, int statMax, int statType) {
		
		this.skin = skin;
		this.skin2 = skin2;
		
		findBackground(statType);
		
		statLabel = new Label(""+stat, skin);
		statLabel.setFontScale(1.5f);
		pBar = new MyProgressBar(skin2, stat, statMax);
		pBar.update(stat);
		
		this.add(statLabel).expand().align(Align.left).padLeft(95).padTop(20);
		this.row();
		this.add(pBar).size(120, 10).expand().align(Align.bottomLeft).padLeft(18).padBottom(20);
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
}
