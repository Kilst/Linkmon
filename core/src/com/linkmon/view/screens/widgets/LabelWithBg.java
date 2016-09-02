package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class LabelWithBg extends Label {
	
	private Image bgImage;

	public LabelWithBg(CharSequence text, Skin skin, Drawable drawable) {
		super(text, skin);
		// TODO Auto-generated constructor stub
		
		bgImage = new Image(drawable);
		this.setAlignment(Align.center);
	}
	
	public void update() {
		//bgImage.setPosition(getX(), getY());
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if(bgImage != null)
			bgImage.getDrawable().draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		super.draw(batch, alpha);
	}
}
