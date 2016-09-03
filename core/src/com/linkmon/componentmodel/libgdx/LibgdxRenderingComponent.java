package com.linkmon.componentmodel.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.linkmon.componentmodel.components.IRenderingComponent;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.helpers.ResourceLoader;
import com.linkmon.model.gameobject.Direction;

public class LibgdxRenderingComponent implements IRenderingComponent {
	
	private Sprite sprite;
	
	private LibgdxAnimationComponent animation;

	private boolean flipped;
	
	public void setSprite(GameObject object) {
		if(sprite == null) {
			sprite = new Sprite(ResourceLoader.getRegionFromId(object.getId()));
			object.setWidth(sprite.getWidth());
			object.setHeight(sprite.getHeight());
			
			sprite.setX(object.getX());
			sprite.setY(object.getY());
		}
	}
	
	public void setAnimation(LibgdxAnimationComponent animation) {
		this.animation = animation;
	}

	@Override
	public void update(GameObject object) {
		// TODO Auto-generated method stub
		
		if(sprite != null)
			updateSprite(object);
		if(animation!= null) {
			updateAnimation(object);
			animation.getCurrentAnimation();
		}
	}

	private void updateAnimation(GameObject object) {
		// TODO Auto-generated method stub
		animation.update(object);
		if(object.direction == Direction.RIGHT)
			flipped = (false);
		else if(object.direction == Direction.LEFT)
			flipped = (true);
	}

	private void updateSprite(GameObject object) {
		// TODO Auto-generated method stub
		if(object.direction == Direction.RIGHT)
			sprite.setFlip(false, false);
		else if(object.direction == Direction.LEFT)
			sprite.setFlip(true, false);
		
		sprite.setX(object.getX());
		sprite.setY(object.getY());
	}

	public void draw(Batch batch, GameObject object) {
		// TODO Auto-generated method stub
		if(sprite != null)
			batch.draw(sprite, sprite.getX(), sprite.getY());
		else if(animation != null)
			if(animation.getCurrentAnimation() != null) {
				if(flipped)
					batch.draw(animation.getCurrentKeyFrame(), object.getX(), object.getY(), animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());
				else
					batch.draw(animation.getCurrentKeyFrame(), object.getX()+animation.getCurrentKeyFrame().getRegionWidth(), object.getY(), -animation.getCurrentKeyFrame().getRegionWidth(), animation.getCurrentKeyFrame().getRegionHeight());	
			}
	}

}
