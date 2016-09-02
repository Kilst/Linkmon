package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.linkmon.view.WorldRenderer;

public class StatWidget extends Table {
	
	private Image statType;
	private Label statAmount;
	private MyProgressBar pBar;
	
	private Skin skin;
	private Skin skin2;

	public StatWidget(Skin skin, Skin skin2, int width, int height) {
		super();
		this.setBackground(skin2.getDrawable("statBackground"));
		this.getColor().a = 0.7f;
		
		pBar = new MyProgressBar(width, 15);
		//pBar.alignMiddle(15);
		
		statType = new Image(skin2.getDrawable("statLabelATK"));
		statType.setAlign(Align.top);
		statAmount = new Label("", skin);
		statAmount.setAlignment(Align.left);
		statAmount.setFontScale(2f);
		
		this.add(statType).fill();
		this.add(statAmount).fill();
		this.row();
		this.add(pBar).colspan(2).size(width, 15).expand().pad(3*WorldRenderer.scaleXY);
		
		this.debug();
	}
	
	public void update(int amount) {
		statAmount.setText(""+amount);
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
	}
}
