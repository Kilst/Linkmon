package com.linkmon.view.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScrollingLabel extends Label {
	
	private CharSequence scrollText;
	private float elapsedTime = 0;
	
	private int counter = 0;
	
	public boolean isFinished = false;
	
	private float textSpeed;
	
	private ScrollingLabel label = this;
	
	private String[] messages;
	
	private int i = 0;

	public ScrollingLabel(CharSequence text, Skin skin, float textSpeed) {
		super("", skin);
		// TODO Auto-generated constructor stub
		scrollText = text;
		this.textSpeed = textSpeed;
		i++;
	}
	
	public ScrollingLabel(String[] messages, Skin skin, float textSpeed) {
		super("", skin);
		// TODO Auto-generated constructor stub
		this.messages = messages;
		this.textSpeed = textSpeed;
		
		scrollText = messages[i];
		i++;
	}

	public void instantText() {
		if(!isFinished) {
    		label.setText(scrollText);
    		counter = scrollText.length();
    	}
	}
	
	public boolean nextMessage() {
		if(i < messages.length) {
			scrollText = messages[i];
			i++;
			isFinished = false;
			this.setText("");
			counter = 0;
			return true;
		}
		else
			return false;
	}
	
	private void updateText() {
		if(counter < scrollText.length() && !isFinished){
			elapsedTime += Gdx.graphics.getDeltaTime();
	        if(elapsedTime > textSpeed){
	            this.setText(this.getText() + "" + scrollText.subSequence(counter, counter+1));
	            elapsedTime = 0;
	            counter++;
	        }
		}
		else
			isFinished = true;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		super.draw(batch, alpha);
		updateText();
	}

}
